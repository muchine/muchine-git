// 사용자 프로필
var layProfile = {
	oThis		: "",
	profileDisplayTimer : null,

	init	: function() {
		this.oThis = $("#layProfile");
	},

	onShow	: function (evt) {
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		var userId = "";
		
		// 크롬오류 대비
		for(var i=0; i<4; i++) {
			userId = $(eTarget).attr("data");
			if (userId > 0) break;
			
			if (i==3) return;
			eTarget = eTarget.parentNode;
		}
		
		/*
		 * 자기자신에 대해서는 Profile이 보여질 필요가 없음.
		 */
		if(User.get("usr_key") == userId) return;	

		var location = layProfile.getProfileLocation(eTarget);
		layProfile.loadProfile(userId, location);
	},
	
	onHide	: function(evt) {
		$(layProfile.oThis).css({'display':'none'});
	},
	
	loadProfile : function(userId, location) {
		$.ajax({
			url		: gContextPath + "/org/user/ui/card/" + userId + ".jstl",
			async	: false,
			cache	: false,
			success : function(response) {
				$("#layProfile").html(response);
				
				var messengerId = layProfile.getMessengerId();
				layProfile.showPresence(messengerId);
				
				$(layProfile.oThis).css({
					'top': location.top + "px", 'left': location.left + "px",
					'display':'block'
				});
			},
			error : function(x, e) {
				console.log("can't get user profile for " + userId);
			}
		});
	},
	
	getProfileLocation : function(eTarget) {
		//var pos  	= $(eTarget).offset();
		var pos		= $ogs.dom.getOffsetLeftTop(eTarget);
		var top		= parseInt(pos.top);
		var left 	= parseInt(pos.left);
		var half_H	= parseInt(document.body.offsetHeight / 2);
		var half_W	= parseInt(document.body.offsetWidth  / 2);

		//-- 프로필 box 위치지정 (크기 : 310x125)
		if(top  < half_H) { top = top + 13; }			// 아래쪽으로 표시
		else 			  { top = top - 125; }			// 위쪽으로 표시

		if(left < half_W) { left = left + 13; }			// 오른쪽으로 표시
		else 			  { left = left + 20 - 310; }	// 왼쪽으로 표시
		
		return {
			top: top,
			left: left
		};
	},
	
	showPresence : function(messengerId) {
		var presence = PNSClient.getPresence(messengerId);

		if(presence == 'available') {
			$("#user-presence").css("color", "#57cd00");
			$("#user-presence").text("(온라인)");
			$("#layPf_pic_onff").css("background-color", "#57cd00");
		} else {
			$("#user-presence").css("color", "#ccc");
			$("#user-presence").text("(오프라인)");
			$("#layPf_pic_onff").css("background-color", "#ececec");
		}
	},
	
	getMessengerId : function() {
		return $("#layPf_header").attr("messenger-id");
	},
	
	redirect : function(url) {
		location.href = url;
	},
	
	fail : function(message) {
		layCmn.msg(message);
	},
	
	makeChatRequest : function(messengerId) {
		if(!messengerId) return;
		if(PNSClient.getPresence(messengerId) != 'available') {
			layCmn.msg("대화 상대가 온라인이 아닙니다.");
			return;
		}
		
		pnsClient.chat(PNSClient.CONST.message.C, messengerId, '');
	},
	
	makeFriendRequest : function(userId) {
		if(!userId) return;
		
		confirm_({
			message :'친구신청을 하시겠습니까?',
			callback :function(result, e) {
				if(!result) return;
				layProfile.sendFriendRequest(userId);
			}
		});
	},
	
	sendFriendRequest : function(userId) {
		$.ajax({
			url: "/org/user/social/friend/request/new.json?friend.id=" + userId,
			type: "post",
			success: function(response) {
				layCmn.msg(response.result.message);
			},
			error: function() {
				layCmn.msg("친구신청이 실패했습니다.");
			}
		});
	}	
};

$(document). ready(function() {
	layProfile.init();

	$(".abcUsr").live("mouseenter", function(evt) {
		layProfile.profileDisplayTimer = setTimeout(
			function() { layProfile.onShow(evt); }
			, 1000
		);
	});
	$(".abcUsr").live("mouseleave", function(evt) {
		clearTimeout(layProfile.profileDisplayTimer);
		layProfile.onHide(evt);
	});

	$("#layProfile").live("mouseenter", function() { this.style.display="block";});
	$("#layProfile").live("mouseleave", function() { this.style.display="none";});
});
