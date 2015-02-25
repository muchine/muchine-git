/**
 * 채팅
 * @author 하영
 */
$(function() {
	var model = {
		jid : null,
		fromId : null,
		gid : null,
		isGroup : false,
		inviteMessage : null,
        inviteDtime: null
	};

	tnMessage = new TopNoti({
        reqUsersMap: {},
        reqUsers: [],
		element : $('a.icoMessage:first'),
		notiIcoClass : 'o-i-am-messege',
        tnMessageObj: null,


        // 채팅 초대 메시지 도착시 상단 노티 메시지 출력
		text : function(message) {
			tnMessage.tnMessageObj = this;
			message = $.extend({}, model, message);

			var refId, retTitle;
			if (message.isGroup === true) {
				refId = message.gid;
				retTitle = '"' + message.fromId + '"님이 그룹채팅에 초대하셨습니다.';
			} else {
				refId = message.fromId;
				retTitle = '"' + refId + '"님이 채팅을 요청하셨습니다.';
			}

			// 카운트 갱신
			layHeader.noti.msg.showCount();

			return retTitle;

//			if (this.reqUsersMap[refId] == null) {
//				// Icon 위의 숫자를 카운팅 한다 (1증가)
//                tnMessage.tnMessageObj.counting(1);
//
//				this.reqUsersMap[refId] = message;
//                message.inviteDtime = abcDtime.getToday();
//				this.reqUsers.push(message);
//
//				// qtip에 보여질 텍스트를 반환한다.
//				return retTitle;
//			}
//
//			return null; // qtip을 출력하지 않는다.
		}
	});
});