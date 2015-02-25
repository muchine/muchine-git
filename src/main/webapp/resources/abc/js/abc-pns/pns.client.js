var PNSClient = (function() {
	// #################################
	// (참고) Schema List
	// 1. Chat State Notifications => http://xmpp.org/schemas/chatstates.xsd
	// #################################

	var $S = Strophe, $NS = $S.NS, $Status = $S.Status;
	var INSTANCE_POOL = {}, PRESENCES = {}, HANDLER_MAP = {};

	/**
	 * Event Types
	 */
	var $E = {
		CONNECTED : 'onConnected',
		CHANGE_CHAT_STATE : 'onChangeChatState',
		GROUP_MESSAGE : 'onGroupMessage',
		GROUP_PRESENCE : 'onGroupPresence',
		INVITE : 'onInvite',
		MESSAGE : 'onMessage',
		NOTIFICATION : 'onNotification',
		PRESENCE : 'onPresence',
		SEND_MESSAGE : 'onSendMessage' // person|group 구분 없음
	};
	/**
	 * My CONST.
	 */
	var $C = {
		events : $E,
		message : {
			C : 'chat',
			GC : 'groupchat'
		},
		namespace : { // Strophe.NS에 없는 것들 추가하였음.
			PNS : 'abc:pns',
			MUC_USER : $NS.MUC + '#user',
			CHAT_STATES : 'http://jabber.org/protocol/chatstates',
			ROSTER : 'abc:roster',
            FILE : 'http://jabber.org/protocol/si'
		},
		presence : {
			AV : 'available',
			UNAV : 'unavailable'
		},
		chatState : {
			ACTIVE : "active",
			COMPOSING : "composing",
			GONE : "gone",
			INACTIVE : "inactive",
			PAUSED : "paused"
		}
	};

	// TODO cookie가 아니라 서버에 저장해야 할 거 같은 불길한 예감이...
	var setCookie = function(key, value) {
		var expires = new Date();
		expires.setTime(expires.getTime() + 31536000000); // 1 year
		document.cookie = key + '=' + value + ';expires=' + expires.toUTCString();
	};

	var getCookie = function(key) {
		key = key.replace('.', '\\.'); // 정규식에선 '.'은 어느 한 글자를 의미한다.
		var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
		return keyValue ? keyValue[2] : null;
	};

	var _addHandler = function(context, type, fn) {

	};

	// =========================================================================================
	// Def.
	// =========================================================================================

	/**
	 * @author 이하영
	 * @class
	 */
	var PNSClient = Class.extend({
		options : {
			id : null, // usr_info.msngId = jid
			token : null,
			jsessionId : null,
			windowGuid : null,            //브라우즈 식별자 id
			rid : null,
			sid : null,
			BOSH_SERVICE : '/xmpp-httpbind',
			browser : new PNSBrowser(),
		},
		/**
		 * @field {Object}
		 */
		connection : null,
		//connectionCnt : 0,
		init : function(options) {
			var t = this;
			var o = t.options = $.extend({}, t.options, options);

			var cid = Strophe.getBareJidFromJid(o.id);
			var _cookie = getCookie(cid);
			if (_cookie && _cookie != '') {
				$.extend(o, JSON.parse(_cookie));
			}

			INSTANCE_POOL[o.id] = this;
            
			t._connect();
		},
		_connect : function() {
			var t = this, o = t.options;
			var c = t.connection = new $S.Connection(o.BOSH_SERVICE);
			if (o.sid && o.rid) {
				c.attach(o.id, o.sid, o.rid, function() {
					return t._onConnect.apply(t, arguments);
				});
			} else {
				
				//브라우즈 1개, 아이디1개,  탭2개 의 경우 스트로피 접속시에 같은 jsessionId로 메신저 서버에 접속을 한다.
				//같은 jsessionid로 두개의 브라우즈에서 접속을 한경우 다른 탭에 있는 연결을 끊는다.
				//끊기 브라우즈에서는 다시 접속을 시도하며, 다른 브라으즈의 접속을 끊는다. 서로 끊어다, 붙였다를 반복한다.
				//텝별로 다른 jsessionid로 접속하기 위하여 windowGuid(탭별고유값)을 붙여서 메신저 서버에 접속한다.
				var jsesionId  = (o.windowGuid ==null)?o.jsessionId:o.jsessionId+"_"+o.windowGuid;
				
				var jid = o.id + '/web_' + jsesionId;
				//var jtoken = o.token + ':web_' + o.jsessionId;
				
				console.log(jid);
				console.log(o.token);
				c.connect(jid, o.token, function() {
					return t._onConnect.apply(t, arguments);
				});
			}
		},
		_disconnect : function(reason) {
			// id에 해당하는 모든 세션이 종료된다.
			this.connection.disconnect(reason);
		},
		/**
		 * Strophe와 연결 이벤트 핸들러
		 *
		 * @param {Integer} status
		 * @param {String} failReason
		 */
		_onConnect : function(status, failReason) {
			var t = this;
            
			console.log("status :"+status);
			console.log("failReason :"+failReason);
			switch (status) {
			case $Status.ERROR: // 0
				console.log("ERROR");
				return;
			case $Status.CONNECTING: // 1
				console.log("CONNECTING");
				return;
			case $Status.CONNFAIL: // 2
				console.log("CONNFAIL", failReason);
				return;
			case $Status.AUTHENTICATING: // 3
				console.log("AUTHENTICATING");
				return;
			case $Status.AUTHFAIL: // 4
				console.log("AUTHFAIL");
				return;
			case $Status.CONNECTED: // 5
				console.log("CONNECTED");
				t._initHandler();
				t._trigger($E.CONNECTED, [ t.options.id ]);
				return;
			case $Status.DISCONNECTED: // 6
				console.log("DISCONNECTED");
				// PNS 연결은 항상 유지되어야 한다.
				// 연결이 되지 않은 경우에는 3초 후에 다시 시도 한다.
				setTimeout(function(){t._connect()}, 3000);
				return;
			case $Status.DISCONNECTING: // 7
				console.log("DISCONNECTING");

				// Connection Attach 실패할 경우 일반 로그인으로 전환한다.
				var o = t.options, c = t.connection;
				o.rid = null, o.sid = null;

				var cid = Strophe.getBareJidFromJid(c.jid);
				setCookie(cid, null);

				return;
			case $Status.ATTACHED: // 8
				console.log("ATTACHED");

				t._initHandler();
				t._trigger($E.CONNECTED, [ t.options.id ]);

				// attached 시에는 친구들의 프레젠스를 즉시 돌려받지 못한다.
				// 따라서 한번 요청을 날려서 가져오도록 하자.
				t._requestFriendsPresence();
				return;
			}
		},
		_initHandler : function() {
			var t = this;
			var c = t.connection;

			c.addHandler(function(e) {
				return t._onMessage.apply(t, arguments);
			}, null, 'message', null, null, null);

			c.addHandler(function() {
				return t._onIq.apply(t, arguments);
			}, null, 'iq', null, null, null);

			c.addHandler(function() {
				return t._onPresence.apply(t, arguments);
			}, null, 'presence', null, null, null);

			// update cookie for connection-session
			c.addHandler(function(e) {
				var cid = Strophe.getBareJidFromJid(c.jid);
				var _data = {
					jid : c.jid,
					sid : c.sid,
					rid : c.rid
				};
				setCookie(cid, JSON.stringify(_data));

                if(e.nodeName === 'presence') {

                    t._onPresence.apply(t, arguments);
                }

				return true;
			}, null, '', null, null, null);

			c.send($pres().tree());
		},
		/**
		 * strophe의 message element를 처리하는 핸들러
		 * @param e
		 * @returns {Boolean}
		 */
		_onMessage : function(e) {
			var t = this, o = t.options;
			var $e = $(e);

			var $i = $e.find('invite:first');
			var $b = $e.find('body:first');
			var from = $e.attr('from');
			var to = $e.attr('to');
			var id = $S.getBareJidFromJid(from);
            //var id = from;

			if ($i.length > 0) {
				t._trigger($E.INVITE,
						[ o.id, $i.attr('from')/*상대아이디*/, from/*그룹아이디*/, $i.find('reason:first').text() /*초대메세지*/]);
			} else if ($b.length === 0) {
				if ($e.find('composing:first').length > 0) {
					t._trigger($E.CHANGE_CHAT_STATE, [ $C.chatState.COMPOSING, id ]);
				}
				if ($e.find('inactive:first').length > 0) {
					t._trigger($E.CHANGE_CHAT_STATE, [ $C.chatState.INACTIVE, id ]);
				}
			} else {
				if ($e.attr('type') === $C.message.GC) {
					var gid = $S.getResourceFromJid(from); // 이 경우 이게 group id가 된다 ㅡ,.ㅡ
					if (gid != null && gid != t.options.id) {
						t._trigger($E.GROUP_MESSAGE, [ $C.message.GC, o.id, id, gid, $b.text() ]);
					}
				} else {
					t._trigger($E.MESSAGE, [ $C.message.C, o.id, id, $b.text() ]);
				}
			}

			return true;
		},
		/**
		 * strophe의 iq element를 처리하는 핸들러
		 */
		_onIq : function(e) {
			var t = this;
			var $e = $(e);

			var $b = $e.find('body:first');
			var $m = $e.find('message:first');
            var $si = $e.find('si:first');

			if ($m.length > 0 && $C.namespace.PNS == $m.attr('xmlns')) {
				var count = $m.attr('count');
				((count == null || count == "") && (count = -1));
				t._trigger($E.NOTIFICATION, [ $m.attr('type'), count, $m.attr('web_yn'), $b.text() ]);
			} else if($C.namespace.FILE == $si.attr('xmlns')) {

                var o = t.options, c = t.connection;

                var $file = $si.find('file:first');

                var iq = $iq({
                    to : $e.attr('from'),
                    type : 'error',
                    id : $e.attr('id')
                }).c('error', {code:'503', type:'Service Unavailable'})
                    .up().c('si', {xmlns:$si.attr('xmlns'), id:$si.attr('id'), profile:$si.attr('profile')})
                    .c('file', {xmlns:$file.attr('xmlns'), name:$file.attr('name'), size:$file.attr('size')});

                c.send(iq.tree());



            }

			return true;
		},
		/**
		 * strophe의 presence element를 처리하는 핸들러
		 */
		_onPresence : function(e) {
			var t = this;
			var $e = $(e);

			var from = $e.attr('from');
			var id = $S.getBareJidFromJid(from);
			var type = $e.attr('type');

			var $s = $e.find('show:first');
			var status = null;
			if (type == null && $s.length > 0) {
				status = $s.text();
			} else if (type == $C.presence.UNAV) {
				status = type;
			} else {
				status = $C.presence.AV;
			}

			var isGroup = false, idInGroup = null;

            var $xlist = $e.find('x');
            var $x = undefined;

            for(var i= 0, cnt=$xlist.length; i<cnt; i++) {

                var $xitem = $($xlist[i]);

                if ($xitem.attr('xmlns') == $C.namespace.MUC_USER) {

                    $x = $xitem;
                    break;
                }
            }

//            var $x = $e.find('x:first');

            if ($x !== undefined) {
//            if ($x.length > 0 && $x.attr('xmlns') == $C.namespace.MUC_USER) {
				var $it = $x.find('item:first');
				if ($it.length > 0) {
					isGroup = true;
					//idInGroup = $S.getBareJidFromJid($e.attr('to'));
                    idInGroup = $S.getResourceFromJid($e.attr('from'));
				}
			}

			var isPresenceChange = false;

			if(!PRESENCES[id]) {
				// presence 목록에 없으면 만들어준다

				if(status !== $C.presence.UNAV) {

					PRESENCES[id] = {};
					PRESENCES[id][from] = status;

					isPresenceChange = true;
				}

			} else {
				if(status === $C.presence.UNAV) {
					// logoff 상태라면 리소스를 삭제한다.

					if(PRESENCES[id][from]) {

						delete PRESENCES[id][from];

						var cnt = 0;
						for(var item in PRESENCES[id])
							cnt ++;

						if(cnt === 0) {
							// 리소스 모두 logoff 되었다면 목록에서 삭제한다.
							delete PRESENCES[id];
						}
					}
				} else {
					PRESENCES[id][from] = status;
				}
			}

			if (isGroup) {
				// TODO presences 테스트 필요
				t._trigger($E.GROUP_PRESENCE, [ id, idInGroup, status ]);
			} else {
				t._trigger($E.PRESENCE, [ id, t.getPresence(id) ]);
			}

			return true;
		},
		_requestFriendsPresence : function() {
			var t = this, o = t.options, c = t.connection;

			var iq = $iq({
				to : o.id,
				type : 'query'
			}).c('message', {
				'xmlns' : $C.namespace.ROSTER
			});
			c.send(iq.tree());
		},
		_trigger : function(eventName, args) {
			var t = this, b = t.options.browser;
			var evt;
			try { // 내가 try,catch 왜 감쌌지?!
				((evt = b[eventName]) && evt.apply(b, args));
			} catch (e) {
				console.log(e.stack);
			}

			var hl = null;
			if ((hl = HANDLER_MAP[eventName])) {
				for ( var i = hl.length - 1; i >= 0; i--) { // 내림차순
					if (hl[i].apply(t, args) === false) {
						break;
					}
				}
			}
		},
		/**
		 * 그룹채팅 참가요청에 수락한다는 내용전달.
		 */
		accept : function(gid) {
			var t = this, o = t.options, c = t.connection;

			var pres = $pres({
				to : gid.concat('/').concat(o.id)
			}).c('x', {
				xmlns : $NS.MUC
			});
			c.send(pres.tree());
		},
		/**
		 * 상대방에게 채팅 메세지를 전송합니다.
		 * @param {Enum} type - PNSClient.CONST.message
		 * @param {String} id - 상대방 아이디 / 그룹 아이디??
		 * @param {String} message
		 */
		chat : function(type, id, message) {
			console.log("##### pns client chat");

			var t = this, o = t.options;

			if (type == 'chat') {
	            var onlineId = t.getOnlineResource(id);
	            if(onlineId)
	                id = onlineId;
			}

			var msg = $msg({
				type : type,
				to : id,
				from : o.id
			}).c('body').t(message).up().c($C.chatState.INACTIVE).attrs({
				xmlns : $C.namespace.CHAT_STATES
			});

			console.log(msg);
			console.log($msg);

			if (message != null && message != "") {
				t.connection.send(msg.tree());
			}

			t._trigger($E.SEND_MESSAGE, [ type, o.id, id, message ]);
		},
		/**
		 * 상대에게 나의 채팅상태를 전송.
		 * @param {Enum} state - PNSClient.CONST.CHAT_STATES
		 * @param {String} id - 상대방 아이디 / 그룹 아이디
		 */
		chatState : function(state, id) {
			var t = this, o = t.options;

			var msg = $msg({
				type : $C.message.C, // 'chat' only ?!
				to : id,
				from : o.id
			}).c(state).attrs({
				xmlns : $C.namespace.CHAT_STATES
			});
			t.connection.send(msg.tree());
		},

        getPresence : function(id) {

            return PNSClient.getPresence(id);
        },

        getOnlineResource : function(id) {

            return PNSClient.getOnlineResource(id);
        }
	});

	PNSClient.CONST = $C;
	PNSClient.getInstance = function(id) {
		var _id = Strophe.getBareJidFromJid(id);
		return INSTANCE_POOL[_id];
	};

	PNSClient.getPresence = function(id) {
        var status = $C.presence.UNAV;

        if (id) {
            var _id = Strophe.getBareJidFromJid(id);

            if(!PRESENCES[_id])
                return status;

            for(var resourceId in PRESENCES[_id]) {

                status = PRESENCES[_id][resourceId];

                // 메신저 우선
                if(resourceId.indexOf('/OnnetMsn2') > 0) {

                    break;
                }

            }
        }

        return status;
	};

    PNSClient.getOnlineResource = function(id) {

        if (id) {
            var _id = Strophe.getBareJidFromJid(id);

            if(!PRESENCES[_id])
                return null;

            var resourceId = null;

            for(resourceId in PRESENCES[_id]) {
            	
                // 메신저 우선
                if(resourceId.indexOf('/BizMessenger') > 0) {

                    return resourceId;
                }
            }

            return resourceId;
        }
    }

	PNSClient.addHandler = function(type, fn) {
		if (!$.isFunction(fn)) {
			throw Error("함수만 핸들러로 등록 가능 하다규");
		}

		var handlerList = null;
		if ((handlerList = HANDLER_MAP[type]) == null) {
			handlerList = HANDLER_MAP[type] = [];
		}
		handlerList.push(fn);
	};
	PNSClient.removeHandler = function(type, fn) {
		var handlerList = null;
		if ((handlerList = HANDLER_MAP[type])) {
			// 해당 핸들러만 삭제
			if (fn) {
				for ( var i = 0, len = handlerList.length; i < len; i++) {
					if (handlerList[i] === fn) {
						handlerList.splice(i, 1);
						break;
					}
				}
			}
			// 전체 핸들러 삭제
			else {
				HANDLER_MAP[type] = [];
			}
		}
	};
	return PNSClient;
})();
