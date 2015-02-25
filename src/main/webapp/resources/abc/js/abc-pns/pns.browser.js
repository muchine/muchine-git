/**
 * @author 하영
 */
var PNSBrowser = (function() {
	/**
	 * 채팅방 여부 확인
	 */
	var hasChat = function(from) {
		console.log("##### PNSBrowser hasChat");
		console.log("from = "+ from);
		var has = ABCChat.getInstance(from) != null;
		console.log("has = "+ has);
		return has;
	};
	/**
	 * 채팅방 생성
	 */
	var newChat = function(chattype, jid, fromId) {
		return new ABCChat({
			id : jid,
			on : fromId,
			chattype : chattype
		});
	};
	/**
	 * 채팅방 가져오기
	 */
	var getChat = function(from) {
		return ABCChat.getInstance(from);
	};

	/**
	 * (PNS <-> 웹 사용자) Browser.Class
	 */
	var PNSBrowser = Class.extend({
		/**
		 * PNS 연결 성공시
		 * @param {String} id - 연결에 사용된 아이디
		 */
		onConnected : function(id) {
			console.log('onConnected', arguments);

			// TODO 친구리스트 ?!
		},
		onInvite : function(jid, fromId, gid, inviteMessage) {
			console.log('onInvite', arguments);
			console.log("gid = "+ gid +", jid = "+ jid +", fromId = "+ fromId +", inviteMessage = "+ inviteMessage);

			if (!hasChat(gid)) {
				console.log("creating group chat request history...");
				// 채팅신청 등록
				$.ajax({
					url: "/usr/chatreq/req?type=groupchat&req_msng_id="+ fromId +"&gid="+ gid,
					type: "get",
					success: function(result) {
						$.ajax({
							url : "/usr/chatreq/findChatRequest/"+fromId+".json",
							type : "get",
							success : function(result){
								var reqName=(result == null)? fromId : result.kor_nm+"("+result.login_id+")";
								tnMessage.message({
									jid : jid,
									fromId : reqName,
									gid : gid,
									isGroup : true,
									inviteMessage : inviteMessage
								});
							}
							
						});
						
						
					}
				});

				newChat(PNSClient.CONST.message.GC, jid, gid);
			}
		},
		onChangeChatState : function(state, id) {
			console.log('onChangeChatState', arguments);

			if (hasChat(id)) {
				getChat(id).chatState(state);
			}
		},
		onGroupMessage : function(chattype, jid, gid, fromId, message) {
//			alert("onGroupMessage");
			console.log('onGroupMessage', arguments);
			console.log("chattype = "+ chattype +", gid = "+ gid +", jid = "+ jid +", fromId = "+ fromId +", message = "+ message);

			if (hasChat(gid)) {
				getChat(gid).input(fromId, message);
			} else {
				newChat(chattype, jid, gid).input(fromId, message);
			}
		},
		/**
		 * 채팅 메세지 수신 시
		 * @param {String} from
		 * @param {String} message
		 */
		onMessage : function(chattype, jid, fromId, message) {
//			alert("onMessage");
			console.log('onMessage', arguments);
			console.log("chattype = "+ chattype +", jid = "+ jid +", fromId = "+ fromId +", message = "+ message);

			var bHasChat = hasChat(fromId);
			console.log("bHasChat = "+ bHasChat);
//			alert("bHasChat = "+ bHasChat);

			if (bHasChat) {
				console.log("채팅방 있음");
				var chat = getChat(fromId);
				chat.input(fromId, message);

				$.ajax({
					url: "/usr/chatreq/addMessage?type="+ chattype +"&req_msng_id="+ fromId +"&message="+ encodeURI(message),
					type: "get",
					success: function(result) {
//									alert("success add message");
					}
				});

//				alert("chat.isAccepted = "+ chat.isAccepted());
//				alert("message = "+ message);
			// 채팅 초대 메시지 수신시
			} else {
//				alert("채팅방 없음");
				console.log("채팅방 없음");

//				$.ajax({
//					url: "/usr/chatreq/getChatroom?room_id="+ fromId,
//					type: "get",
//					success: function(result) {
//						console.log("##### getChatroom success");
//						console.log(JSON.stringify(result));
//					}
//				});

				// 채팅방 생성
				var chat = newChat(chattype, jid, fromId);
				console.log("chat.isAccepted = "+ chat.isAccepted());
//				alert("chat.isAccepted = "+ chat.isAccepted());
				chat.input(fromId, message);
				console.log("chat.isAccepted = "+ chat.isAccepted());
//				alert("message = "+ message);

				if (chat.isAccepted() === false) {
					console.log("creating chat request history...");
					// 채팅신청 등록
					$.ajax({
						url: "/usr/chatreq/req?type="+ chattype +"&req_msng_id="+ fromId,
						type: "get",
						success: function(result) {
							$.ajax({
								url: "/usr/chatreq/findChatRequest/"+fromId+".json",
								type: "get",
								success:function(result){
									var reqName=(result == null)? fromId : result.kor_nm+"("+result.login_id+")";
									console.log("reqName : "+reqName);
									tnMessage.message({
										jid : jid,
										fromId : reqName
									});
								}
							});
						
							$.ajax({
								url: "/usr/chatreq/addMessage?type="+ chattype +"&req_msng_id="+ fromId +"&message="+ encodeURI(message),
								type: "get",
								success: function(result) {
//									alert("success add message");
								}
							});
						}
					});
				}
			}
		},
		/**
		 * 내가 메세지를 발송했을 시
		 * @param chattype
		 * @param jid
		 * @param fromId
		 * @param message
		 */
		onSendMessage : function(chattype, jid, fromId, message) {
			console.log('onSendMessage', arguments);
			console.log("chattype = "+ chattype +", jid = "+ jid +", fromId = "+ fromId +", message = "+ message);

			var bareFromId = Strophe.getBareJidFromJid(fromId);
			console.log("bareFromId = "+ bareFromId);

			if (hasChat(bareFromId)) {
				getChat(bareFromId).echo(message);
			} else {
				var chat = newChat(chattype, jid, bareFromId);
				chat.accept();
				chat.echo(message);
			}
		},
		onNotification : function(type, count, web_yn, message) {
//			alert("onNotification");
			console.log('onNotification', arguments);
			console.log('type = '+ type +", count = "+ count +', web_yn = '+ web_yn +", message = "+ message);

			var notis = {
				apr : tnSign,
				sch : tnCal,
				note : tnNote,
				jnl : tnTodo,
				mail : tnMail,
				frnd : tnAdd
			};

			var workType = type.split('_')[0].toLowerCase();
			var noti = notis[workType] || tnDummy;

            if(web_yn != "N")
            	noti.message(message);

			console.log("workType = "+ workType);

			// 노티 카운트
			if (workType == "note") {
				layHeader.noti.note.showCount();
				if (typeof wgTodo == "object") {
					wgTodo.init();
				}
				if (typeof wgNot == "object") {
					wgNot.init();
				}
				if (typeof notRecvbox == "object") {
					if(tabStripCtrl_not.get_cnfgArr()[0].type == "notRecvbox")
						notRecvbox.clickSearch();
				}

			} else if (workType == "mail") {
				layHeader.noti.mail.showCount();
				if (typeof wgTodo == "object") {
					wgTodo.init();
				}
				if (typeof wgMai == "object") {
					wgMai.init();
				}

			} else if (workType == "apr") {
				layHeader.noti.apr.showCount();
				if (typeof wgTodo == "object") {
					wgTodo.init();
				}
				if (typeof wgApr == "object") {
					wgApr.init();
				}
				if (typeof goingDocList == "object") {
					goingDocList.clickSearch();
				}

			} else if (workType == "sch") {
				layHeader.noti.sch.showCount();
				if (typeof wgTodo == "object") {
					wgTodo.init();
				}
				if (typeof wgCalendar == "object") {
					wgCalendar.init();
				}

			} else if (workType == "jnl") {
				layHeader.noti.todo.showCount();
				if (typeof wgTodo == "object") {
					wgTodo.init();
				}
				if (typeof wgTask == "object") {
					wgTask.init();
				}
				if (typeof wgRequest == "object") {
					wgRequest.init();
				}

			} else if (workType == "frnd") {
				layHeader.noti.frndreq.showCount();

			} else if (workType == "brd") {
				if (typeof wgBrd == "object") {
					wgBrd.init();
				}
			}

			if (typeof myworkMain == "object") {
				myworkMain.refreshCurrentFeedList();
			}

//			if (count != -1) {
//				noti.count(count);
//			}
		},
        onGroupPresence : function(id, idInGroup, status) {
            console.log('onGroupPresence', arguments);

            if (hasChat(id)) {
                getChat(id).presence(status, idInGroup);
            }
        },
		onPresence : function(id, status) {
			console.log('onPresence', arguments);

			if (hasChat(id)) {
				getChat(id).presence(status);
			}
		}
	});

	return PNSBrowser;
})();