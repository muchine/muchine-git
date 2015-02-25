// 프로필
var wgProfile = {
	init: function() {
		var url = "/common/widget/profile/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_profile"));
	}
};

// 전자결재
var wgApr = {
	init: function() {
		var url = "/common/widget/approval/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_apr"));
	}
};

//메일
var wgMai = {
	init: function() {
		var url = "/common/widget/mail/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_mail"));
	}
};

//게시판
var wgBrd = {
	init: function() {
		$(".wg_brd").each(function(index) {
			var url ="/common/widget/board/" + $(this).parent().attr("data-item") + ".jstl";			
			widgetAll.paintWidgetInContainer(url, $(this));
		});
	}
};

// 할일 요청
var wgTask = {
	init: function() {
		var url = "/common/widget/todo/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_task"));
	}
};

// 작업 요청
var wgRequest = {
	init: function() {	
		var url = "/common/widget/task/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_request"));
	}
};

// 근태관리
var wgWrk = {
	init: function() {
		var url = "/common/widget/attendance/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_wrk"));
	}
};

// 포토앨범
var wgPoto = {
	init: function() {
		$(".wg_poto").each(function(index) {
			var url ="/common/widget/photo/" + $(this).parent().attr("data-item") + ".jstl";			
			widgetAll.paintWidgetInContainer(url, $(this));
		});
	}
};

// 쪽지
var wgNot = {
	init: function() {
		var url = "/common/widget/note/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_note"));
	}
};

// 친구
var wgFrnd = {
	init: function() {
		var url = "/common/widget/friend/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_group"));
	}
};

//달력 이미지
var wgCal = {
	init : function() {
		$(".k-wg_cal").kendoCalendar();
		var url = "/common/widget/calendar/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_cal .cal_result"));
	}
};

// 캘린더 (일정관리)
var wgCalendar = {
	init : function() {
		var url = "/common/widget/event/0.jstl";
		//widgetAll.paintWidgetInContainer(url, $(".wg_calendar tbody"));
		widgetAll.paintWidgetInContainer(url, $(".wg_calendar .wgContents"));
	}
};

// to-do
var wgTodo = {
	init : function() {
		var url = "/common/widget/todo-graph/0.jstl";
		widgetAll.paintWidgetInContainer(url, $(".wg_todo"));
	}	
};

var widgetAll = {
	init : function() {
		wgWrk.init();
		wgProfile.init();
		wgApr.init();
		wgMai.init();
		wgBrd.init();
		wgTask.init();
		wgRequest.init();
		wgPoto.init();
		wgNot.init();
		wgFrnd.init();
		wgCal.init();
		wgCalendar.init();
		wgTodo.init();
		
		widgetAll.bindXButtonShowHandler();
		widgetAll.bindXButtonHideHandler();
				
		// 마우스 오버 시, 이동 커서로 변경
		$(".wgTop, .profileTop").live("mouseenter", function() {$(this).css("cursor", "move");});
		$(".wgTop, .profileTop").live("mouseleave", function() {$(this).css("cursor", "pointer");});
	},
	
	bindXButtonShowHandler : function() {
		$(".wgBx").live("mouseenter", function(evt) {
			// 일반사용자인 경우
			if(gUsr_pwr_cd != 2) {
				var widgetId = $(this).parent().attr("id");
				if(!widgetId) return;
					
				var tokens = widgetId.split("_");
				if(tokens.length != 4) return;
					
				// 전사위젯은 삭제불가
				if(tokens[1] != "usr") return;
			}
			
			$(this).find('.wg_close').css("display", "block");
		});
	},
	
	bindXButtonHideHandler : function() {
		$(".wgBx").live("mouseleave", function(evt) {
			$(this).find('.wg_close').css("display", "none");
		});
	},
	
	/*
	 * All widget containers should invoke this method to bind widget removal event handler
	 */	
	bindWidgetRemovalEventHandler : function(handler) {
		$(".wgBx .wg_close").live("click", function(evt) {
			var widget = $(this).closest("li.wg_li");
			widgetAll.removeWidget(widget, handler);
		});
	},
	
	paintWidgetInContainer : function(fetchUrl, containers) {
		if (!widgetAll.containerExists(containers)) return;
		
		$.ajax({
			type: "get",
			url: fetchUrl,
			success: function(response) {
				$(containers).each(function(i) {
					$(this).empty().html(response);
				});
			},
			error: function(response) {
				
			}
		});
	},
	
	containerExists : function(containers) {
		return $(containers).length > 0;
	},
	
	removeWidget : function(widget, handler) {
		var url = $(widget).attr("remove-url");
		var params = widgetAll.createWidgetRemovalParameters(widget);
		
		widgetAll.sendWidgetRemovalRequest(url, params, function(response) {
			handler(widget);
			widgetAll.eraseWidgetFromContainer(widget);
		});
	},
	
	createWidgetRemovalParameters : function(widget) {
		return {
			_method : "DELETE",
			category : $(widget).attr("data-category"),
			type : $(widget).attr("data-type"),
			itemId : $(widget).attr("data-item")
		};
	},
	
	sendWidgetRemovalRequest : function(url, params, callback) {
		$.ajax({
			type: "post",
			url: url,
			data: $.customParam(params),
			success: callback
		});
	},
	
	eraseWidgetFromContainer : function(widget) {
		$(widget).remove();
		$(".widget-containable").sortable("refresh");
	}
	
};