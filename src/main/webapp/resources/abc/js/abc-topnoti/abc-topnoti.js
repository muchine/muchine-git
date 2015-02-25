var TopNoti = (function() {
	var
		/**
		* noti objects "QUEUE(GLOBAL)"
		*/
		GQ = [],
		/**
		* Timer ID
		*/
		ST = null,
		/**
		* Hiding Timeout
		*/
		TIMEOUT = 7000; // lucky seven -_-

	var QUEUE_NEXT = function() {
		if (ST != null) {
			clearTimeout(ST);
			ST = null;
		}

		var inst = GQ.splice(0, 1)[0];
		(inst && inst._show());
	};

	/**
	* qtip 생성자 옵션
	*/
	var qtipOptions = {
		// destroyed : true,
		content : {
			text : ' ' // not empty
		},
		position : {
			my : 'top center',
			at : 'bottom center',
			adjust : {
				x : -5, // -70, // -162
				y : 4
			}
		},
		style : {
			classes : 'qtip-blue',
			tip : {
				corner : false
			}
		},
		show : {
			event : null,
			effect : function(offset) {
				$(this).slideDown(100); // "this" refers to the tooltip
			}
		},
		hide : {
			event : null
		},
		events : {
			render : function(e, api) {
				api.elements.tooltip.addClass('alertMessege').css({
					'margin-left' : '0px',
					'height' : 'auto',
					'padding-bottom' : '7px'
				});
				api.elements.content.css({
					'padding' : '0px'
				});
			},
			hidden : QUEUE_NEXT
		}
	};

	/**
	* @Class
	* @author 하영
	*/
	var TopNoti = Class.extend({
		options : {
			element : null, // {jQuery Object} noti icon class
			notiIcoClass : null, // {String | Function(message)}
			text : function(message) {
				return message;
			},
			onIconClick : null, // noti icon click
			onClick : null //qtip click
		},
		_$cnt : null, // span.count Element
		_countNumber : 0,
		_q : null, // jQuery "q-tip" plugin
		_mq : null, // message "QUEUE"
		init : function(options) {
			var t = this;
			$.extend(t, {}, t.options, options);

			// qtip 준비
			var $lnbBx = $('.LnbBx');
			var $lnb = $lnbBx.find('ul.Lnb:first');
			var e = t.element;

			// qtip의 포지션 세팅~ 잇힝
			var _qOpts = {
				position : {
					target : $lnb,
					container : $lnbBx
				}
			};
			// qtip 생성~ 잇힝
			t._q = e.qtip($.extend(true, {}, qtipOptions, _qOpts)).data('qtip');
			
			/*
			 * If notification counter does not exist since the module of counter is disabled, _q is undefined.
			 * In this case, event binding should not occur on this counter.
			 */
			if (t._q == undefined) return;
			
			// counting icon
			t._$cnt = e.prev();
			if (t._$cnt.length === 0) {
				t._$cnt = $('<span class="count" style="display:none;">0</span>').insertBefore(e);
			}

			// message queue
			t._mq = [];

			t._bindEvent();
		},
		_bindEvent : function() {
			var t = this;

			// Noti icon :click
			t.element.click(function(e) {
				(t.onIconClick && t.onIconClick(e));
				e.preventDefault();
			});

			// qtip :click
			$('body').on('click', '#qtip-' + t._q.id, function(e) {
				// 일단 기본적으로 툴팁을 hide 시키고 보자 ㅡ.ㅡ
				t.hide();

				(t.onClick && t.onClick(e));
				e.preventDefault();
			});
		},
		/**
		* 지정시간 이후에 qtip이 사라진다.
		*/
		_timedHide : function() {
		var t = this;
			ST = setTimeout(function() {
				ST = null;
				t._q.hide();
			}, TIMEOUT);
		},
		/**
		* message queue processor
		*/
		_show : function() {
			if (ST == null) {
				var t = this;
				var message = t._mq.splice(0, 1)[0];
				if (message) {
					var text = t.text.call(t, message);
					if (text) {
						t._q.set({
							'content.text' : function() {
								var icoClass = '';
								if ($.isFunction(t.notiIcoClass)) {
									icoClass = t.notiIcoClass(message);
								} else {
									icoClass = t.notiIcoClass;
								}

								text = text.replace("systemadmin@abc.onnet21.com", "시스템관리자");
								var _html = '<div class="o-i-am-textbox">';
								_html += '<span class="o-i-am-icon ' + (icoClass || '') + '">&nbsp;</span>';
								_html += text;
								_html += '</div>';
								return _html;
							}
						});
						t._q.show();
						t._timedHide();
					} else {
						QUEUE_NEXT();
					}
				}
				// else QUEUE_NEXT()를 해줘야 하나?
			}
		},
		_count : function() {
			var t = this, c = t._$cnt;

			(t._countNumber < 0 && (t._countNumber = 0)); // 0보다 작을 수 없다.

			c.text(t._countNumber);
			(t._countNumber === 0 ? c.hide() : c.show());
		},
		/**
		* arg 숫자로 카운트를 설정한다.
		* @param count
		*/
		count : function(count) {
			if (count < 0) return;
			this._countNumber = count;
			this._count();
		},
		/**
		* arg 숫자만큼 현재 카운트 숫자를 더하거나 뺀다.
		* @param count
		*/
		counting : function(count) {
			this._countNumber += count;
			this._count();
		},
		/**
		* <pre>
		* TopNoti 인스턴스에 메세지를 전달한다.
		* 이때 메세지는 qtip에 보여질 텍스트를 의미하는게 아니다.
		* 규약된 데이터 구조체를 의미할 수 있다. 뭐.. 평문 텍스트일 수도 있고.. (객체)
		* </pre>
		*/
		message : function(message) {
			var t = this;
			if (t._q) {
				t._mq.push(message);
				GQ.push(t);

				if (ST === null) {
					QUEUE_NEXT();
				}
			}
		},
		hide : function() {
			this._q.hide();
		}
	});

	return TopNoti;
})();