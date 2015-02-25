/**
 * @author 하영
 */
var ABCChat = (function() {
	var INSTANCE_POOL = {};
	var CHAT_LENGTH =4000;
	
	var POSITION = {
		TOP_LEFT : 1,
		TOP_CENTER : 2,
		TOP_RIGHT : 3,
		MIDDLE_LEFT : 4,
		MIDDLE_CENTER : 5,
		MIDDLE_RIGHT : 6,
		BOTTOM_LEFT : 7,
		BOTTOM_CENTER : 8,
		BOTTOM_RIGHT : 9
	};

	var ABCChat = Class.extend({
	options : {
		id : null, // 내 아이디
		on : null, // 상대 아이디 or 그룹 아이디,
		chattype : null, // PNSClient.CONST.message
		width : 300,
		height : 200
	// TODO 시작 위치
	// 시간있고 능력되는 사람이 구현해 주길..
	// position : POSITION.BOTTOM_RIGHT
	},
	
	
	$w : null, // kendo window object
	$in : null, // chat input field
	$out : null, // chat output display area
	$out_bline : null, // chat display helper
	$cs_icon : null, // chat-state icon
	$cs_text : null, // chat-state text
	$pre : null, // title bar - presence area
	$title : null, // title bar - text area
	_writer : null,
	_accepted : false,
    groupMember : [],
    
	init : function(options) {
		console.log("##### abc chat init");
		

		var t = this;
		var o = t.options = $.extend({}, t.options, options);

		console.log("o.on = "+ o.on);
		console.log("ChatOn.containKey(o.on) = "+ ChatOn.containKey(o.on));

		if (ChatOn.containKey(o.on) !== undefined) {
			t._accepted = true;
		}

		console.log("_accepted = "+ t._accepted);

		INSTANCE_POOL[o.on] = this;

		var _html = '<div class="chat-content">';
		_html += '<div class="chat-out-wrap" style="height:' + o.height + 'px;"><div class="chat-out-body">';
		_html += '  <div class="chat-out"></div>';
		_html += '  <div class="chat-out-bline" tabindex="0">';
		_html += '    <span class="chat-state-icon k-icon k-i-pencil"></span>';
		_html += '    <span class="chat-state-text">&nbsp;</span>';
		_html += '  </div>';
		_html += '</div></div>';
		_html += '<div class="chat-in"><textarea class="k-textbox" tabindex="0" rows="2" style="overflow-y: auto;"></textarea></div>';
		_html += '</div>';

		var e = $(_html).kendoWindow({
			actions : [ "Minimize", "Close" ],
			width : o.width,
			resizable : false,
			dragstart : function() {
				var offset = wrap.offset();
				wrap.removeClass('-chat-initializer').css({
					'top' : offset.top,
					'left' : offset.left
				});
			}
		});
		e.focus(function() {
			t.$in.focus();
			return false;
		});

		t.$out = e.find('.chat-out:first');
		t.$out_bline = e.find('.chat-out-bline:first');
		t.$cs_icon = t.$out_bline.children('.chat-state-icon:first').hide();
		t.$cs_text = t.$out_bline.children('.chat-state-text:first');
		t.$in = e.find('.chat-in textarea:first');

		t.$w = e.data('kendoWindow');
		t.$pre = $('<span class="chat-presence">&nbsp;</span>');
		t.$title = $('<span class="chat-title">&nbsp;</span>');
		$('<div class="chat-titlebar">').append(t.$pre).append(t.$title).appendTo(
			t.$w.wrapper.find('span.k-window-title:first'));

		if (o.chattype === PNSClient.CONST.message.C) {
			// 상대방의 초기 프레젠스
			t.presence(PNSClient.getPresence(o.on));
			// 타이틀 제목 초기 값
			//t.title(o.on.split('@')[0]);
			console.log("o.on : " + o.on);
			$.ajax({
				url: "/usr/chatreq/findChatRequest/"+o.on+".json",
				type: "get",
				success:function(result){
					var clientTitle = (result == null)? id : result.kor_nm+"("+result.login_id.split('@')[0]+")";
					t.title(clientTitle);
				}
			});
		} else {
			t.$pre.hide();
			t.title('그룹채팅');
		}

		var wrap = t.$w.wrapper.addClass('-chat-initializer chat-window');
		wrap.get(0).style.left = '';
		wrap.get(0).style.top = '';

		t._bindEvent();

		t.open();
	},
	_bindEvent : function() {
//		console.log("##### abc chat _bindEvent");

		var t = this, o = t.options;
		var client = PNSClient.getInstance(o.id);

		/**
		 * 메세지 입력 필드에서 엔터키 입력시 전송
		 */
		t.$in.keypress(function(e) {
			if (e.keyCode === 13 && e.shiftKey === false) { // ENTER
				var v = $.trim(t.$in.val());
				if (v !== "") {
					//채팅 최대 길이 만큼만 채팅 서버에 발송한다.
					console.log(v.getCutStrByte(CHAT_LENGTH));
					v = v.getCutStrByte(CHAT_LENGTH); 
					t.$in.val('');
					t.output(v);
				}
	
				//e.preventDefault();
				return false;
			} else {
				// TODO 인터벌
				//client.chatState(PNSClient.CONST.chatState.COMPOSING, o.on);
			}
		});
	},
	
	//채팅 내용을 화면에 뿌려 주는 함수
	_write : function(id, m) {
		//id : 메세지를 작성한  ID
		//o.id : 로그인한 ID
		//t._writer : 메세지를 마지막으로 작성한 작성자 id
		//연속을로 메세지를  발송할 경우 메세지 내용만 표시를 해주기 위하여 id를 구분한다.
		
		//메신저에서 자리수가  많은 경우  buffer사이즈로 인하여 죽는 경우가 발생함
		var t = this, o = t.options;
		t.open();

		
		if (t._writer !== id) {   //마지막 작성자와 메세지 작성자가 동일 하지 않은경우에만 작성자 정보을 보여준다.
			var from = null;
			var _html="";
			var p_css = 'write_pass';
			
			
			if (id === o.id) {
				p_css += ' my';
				from = User.get('kor_nm')+'('+User.get('msng_id').split('.')[0]+')';
				t.messageTitle(p_css,from);
				t.messageBody(m);
				//$(_html).appendTo(t.$out);
				
			} else {
				//그룹체팅의 경우 아이디 '_BizMessenger' 가 붙어서 들어온다.
				console.log("id :" +id);
				console.log("o.id :" +o.id);
				var index  = id.indexOf("BizMessenger");
				if(index != -1)  id = t.getUserId(id, o.id);
				console.log("id :" +id);
				
			
				$.ajax({
					url: "/usr/chatreq/findChatRequest/"+id+".json",
					type: "get",
					success:function(result){
						from=(result == null)? id : result.kor_nm+"("+result.login_id.split('@')[0]+")";
						_html=t.messageTitle(p_css,from);
						$(_html).appendTo(t.$out);
						t.messageBody(m);
						
					}
				});
				//from = id.split('@')[0];
			}
			t._writer = id;
		}else{  //최근 작성자와 메세지를 작성한 id가 동일한 경우 메세지만 뿌려 준다.
			t.messageBody(m);
		}
		
		t.$out_bline.focus();
		t.$in.focus();
	},
	messageTitle : function(p_css,from){
		var _html = '<p class="' + p_css + '">';
		_html += '<span class="from">' + from + '</span>';
		// TODO 서버시간으로 바꿀 것.
		_html += '<span class="ts">' + kendo.toString(new Date(), 'd(ddd) tt h:mm') + '</span></p>';
		$(_html).appendTo(this.$out);
		//return _html;
	},
	messageBody : function(message){
		if (message != "" && message != null) {
			//채팅 최대 길이 만큼만 화면에 뿌려 준다.
			message = message.getCutStrByte(CHAT_LENGTH);
//			$('<p class="t" style="white-space:pre; word-break:break-all;">' + m + '</p>').appendTo(t.$out);
			console.log("t.$out : " + this.$out );
			$('<p class="t">' + message + '</p>').appendTo(this.$out);
		}
		
	},
	//메신저 리스소 정보 들어 있는 아이디 정보를 추출한다.
	//orginalId : hawkshim4.abc.onnet21.com_BizMessenger:1.0.0.12 형태로 들어옴
	//compareId : 로그인 사용자 아이디
	getUserId : function(orginalId,compareId){
		var startIndex  = compareId.indexOf("@");
		var tmpStr = compareId.substring(startIndex+1,compareId.length);
		
		return orginalId.substring(0,orginalId.indexOf("BizMessenger")-1)+"@"+tmpStr;;
	},
	presence : function(p, id) {
//		console.lo`("##### abc chat presence");

        var o = this.options;

        if (o.chattype === PNSClient.CONST.message.C) {
		// TODO icon으로 바꿀것
            var t = this;
            if (p === PNSClient.CONST.presence.UNAV) {
                t.$pre.css('background-image', 'url("/resources/abc/images/ico_dept.png")');
            } else {
                t.$pre.css('background-image', 'url("/resources/abc/images/ico_user.png")');
            }
        } else {

            var t = this;

            if(o.id !== id)
            {
                var index = t.groupMember.indexOf(id);

                if(index != -1) {

                    if (p === PNSClient.CONST.presence.UNAV) {

                        t.groupMember.splice(index, 1);
                    }
                }
                else {

                    if (p !== PNSClient.CONST.presence.UNAV) {

                        t.groupMember.push(id);
                    }
                }
            }
        }
	},
	chatState : function(state) {
//		console.log("##### abc chat chatState");
/*
		var t = this;
		var s = PNSClient.CONST.chatState;

		switch (state) {
			case s.COMPOSING:
				t.$cs_icon.show();
				t.$cs_text.text('is typing...');
				break;
			case s.INACTIVE:
			case s.ACTIVE:
				t.$cs_icon.hide();
				t.$cs_text.text('paused typing.');
				break;
			case s.COMPOSING:
				break;
			case s.GONE:
				break;
			case s.PAUSED:
				break;
		}
*/
	},
	title : function(t) {
//		console.log("##### abc chat title");

		this.$title.html(t);
	},
	open : function() {
//		console.log("##### abc chat open");

		if (this._accepted) {
		this.$w.open();
		}
	},
	close : function() {
//		console.log("##### abc chat close");

		this.$w.close();
	},
	/**
	 * 내가 전송한 메세지이다.
	 */
	echo : function(message) {
//		console.log("##### abc chat echo");

		this._write(this.options.id, message);
	},
	/**
	 * 상대방에게 받은 메세지이다.
	 */
	input : function(id, message) {
//		console.log("##### abc chat input");

		var t = this, o = t.options;

		// 그룹채팅은 수락을 해야 쓸 수 있다.
		if (o.chattype === PNSClient.CONST.message.C || t._accepted === true) {
            t.$w.restore();
			t._write(id, message);
		}
	},
	/**
	 * 내가 전달할 메세지이다.
	 */
	output : function(message) {
		console.log("##### abc chat output");
//		alert("abc.chat output");

		var o = this.options;
		var client = PNSClient.getInstance(o.id);
		console.log("##### o.chattype = "+ o.chattype +", o.on = "+ o.on +", message = "+ message);
		client.chat(o.chattype, o.on, message);

		console.log("chattype = "+ o.chattype +", groupMember.length = "+ this.groupMember.length);
		console.log("groupMember = "+ this.groupMember);

        if(o.chattype === PNSClient.CONST.message.GC && this.groupMember.length === 0) {
//			  alert("대화방에 아무도 없어요");
            this.echo('( 현재 대화방에 아무도 없습니다. 메시지가 전달되지 않을 수 있습니다. )');
        }
	},
	/**
	 * 수신 사용자가 accept를 하기 전까지 화면에 출력하지 않도록 한다.
	 */
	accept : function() {
//		console.log("##### abc chat accept");

		var t = this, o = t.options;

		console.log("o.on = "+ o.on +", chattype = "+ o.chattype);

		// 채팅 수락시 이력을 세션에 저장하여 다음번 채팅이 왔을때
		// 바로 채팅창을 띄울 수 있도록 한다. (세션이 유지되는 한..)
//		if (o.chattype == PNSClient.CONST.message.C || o.chattype == PNSClient.CONST.message.GC) {
		if (o.chattype === PNSClient.CONST.message.C) {
			ChatOn.put(o.on);
		}

		t._accepted = true;
		t.open();
	},
	isAccepted : function() {
//		console.log("##### abc chat isAccepted");

		return this._accepted;
	}
	});

	/**
	 * 방위 위치 지정자
	 */
	ABCChat.POSITION = POSITION;
	ABCChat.getInstance = function(id) {
		console.log("##### ABCChat.getInstance");
		console.log("id = "+ id);

		if (id) {
			var _id = Strophe.getBareJidFromJid(id);
			console.log("_id = "+ _id);

			return INSTANCE_POOL[_id];
		}
	};


	return ABCChat;
})();