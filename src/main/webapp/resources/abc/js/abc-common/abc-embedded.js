var embeddedManager = {
	
	appendCustomEmbeddedContent : function(formId, contentHtml) {
		$("#" + formId).remove();
		$("#embeddedCreation").after(contentHtml);
		
		$("#" + formId).addClass("custom-embedded-form");
	},
	
	appendCustomEmbeddedContentByUrl : function(formId, url) {
		$.ajax({
			type: "get",
			url: url,
			async: false,
			success: function(response) {
				embeddedManager.appendCustomEmbeddedContent(formId, response);
			}
		});
	},
	
	hideCustomEmbeddedContent : function() {
		$(".custom-embedded-form").css({display:"none", right:-840});
	},
		
	openEmbeddedCreationForm : function(embedderId, embedderName, providerId, contentId) {
		if (contentId.length == 0) {
			embeddedManager.showFetchContentError("내용을 조회할 수 없습니다.");
			return;
		}
		
		$.ajax({
			type: "get",
			url: "/common/embedded/" + embedderId,
			data: $.customParam({"providerId": providerId, "contentId": contentId}),
			async: false,
			success: function(response) {
				embeddedManager.showEmbeddedHeader("embeddedCreationTitle", embedderName);
				embeddedManager.showEmbeddedContent("embeddedCreation");
				embeddedManager.loadEmbeddedCreationForm(response);
			},
			error: function(response, status, error) {
				embeddedManager.handleFetchContentError(response);
			}
		});
	},
	
	loadEmbeddedCreationForm : function(contentHtml) {
		$("#embeddedCreationContent").html(contentHtml);
	},
	
	handleFetchContentError : function(response) {
		var message = "조회하는 중에 오류가 발생했습니다.";
		if (response.status == 404 || response.status == 403) {
			message = JSON.parse(response.responseText).message;
		}
		
		embeddedManager.showFetchContentError(message);
	},
	
	showFetchContentError : function(message) {
		var params = {
			msg: message,
			callback : function() {}
		};
		
		if (message.length > 20) {
			layCmn.alert(params);
		} else {
			layCmn.msg(params);	
		}
	},
		
	hideAllEmbeddedPopups : function() {
		$("#embeddedCreation").css({display:"none", right:-840});
		$("#embeddedPopup").css({display:"none", right:-840});
		embeddedManager.hideCustomEmbeddedContent();
	},
	
	closeEmbeddedPopup : function(layerId, containerId) {
		$("#" + layerId).animate({right:-640, opacity:1}, 'fast', 'swing', function() { $("#" + layerId).hide(); });
		$("#" + containerId).html("");
	},
	
	closeEmbeddedContent : function() {
		embeddedManager.closeEmbeddedPopup("embeddedPopup", "embeddedPopupContent");
	},
	
	closeEmbeddedCreationForm : function() {
		embeddedManager.closeEmbeddedPopup("embeddedCreation", "embeddedCreationContent");
	},
	
	showEmbeddedHeader : function(titleId, embedderName) {
		$("#" + titleId).text(embedderName);
	},
		
	showEmbeddedContent : function(containerId) {
		var popContentHeight = embeddedManager.calculateEmbeddedContentHeight();
		
		$(".myRightPopContentOutBx").css({'height': popContentHeight + "px"});
		$(".myRightPopContentOutBx").css({'overflow-x':'hidden'});
		$("#" + containerId).css("display", "block");
		$("#" + containerId).animate({right:0},'slow');
	},
		
	calculateEmbeddedContentHeight : function() {
		var popAreaHeight	= layCmn.getHeight_winRightPopArea();		// 가용가능한 팝업윈도우 높이
		var popTitle		= 29;
		var popHeader		= 30;
		var popContsPadding = 20;
		return popAreaHeight - popTitle - popHeader - popContsPadding;	// 팝업 내용영역 높이
	},
	
	/*
	 * This method is temporal until contentIds are inserted into all feed in mongo db. 
	 */
	getContentIdFromLink : function(type, link) {
		var idx = null;
		var idx2 = null;
		var moduleSeq = null;
		
		if (type.indexOf("approval") >= 0) {		
			idx = link.indexOf("/apr/doc/");
			idx2 = link.indexOf(".jstl");
			moduleSeq = link.substring(idx+9, idx2);
		} else if (type.indexOf("note") >= 0) {			
			idx = link.indexOf("/not/read.jstl?id=");
			idx2 = link.indexOf("tabGroup");
			moduleSeq = link.substring(idx+18, idx2-1);
		} else if (type.indexOf("mail") >= 0) {			
			moduleSeq = link;
		} else if (type == "calendar") {
			idx = link.indexOf("/sch/route?id=");
			idx2 = link.indexOf("&period.startDate");
			moduleSeq = link.substring(idx+14, idx2);
		} else if (type.indexOf("board") >= 0) {			
			idx = link.indexOf("/brd/atcl/show/");
			idx2 = link.indexOf("tabGroup");
			moduleSeq = link.substring(idx+15, idx2-1);
		} else if (type.indexOf("journal") >= 0) {			
			idx = link.indexOf("/jnl/info/");
			idx2 = link.indexOf("show");
			moduleSeq = link.substring(idx+10, idx2-1);			
		} else if (type.indexOf("talk") >= 0) {			
			moduleSeq = link;
		}
		
		return moduleSeq;
	}
};
