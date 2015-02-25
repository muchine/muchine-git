//=== 정보보기
var layMaiPopup = {
	cnfgWinId : "layMaiPopup",
	init : function() {

	},
	// 특정 area 에 url 호출
	load_htmlPage : function(page, url) {
		$.ajax({
			url : url,
			success : function(html) {
				$(page).html(html);
				// hideLoading();
			},
			error : function() {
				hideLoading();
			}

		});
		return true;
	},
	
	goService : function(){ // 메일보기화면으로 이동
		var mail_seq = entity.mail_seq;
		var from = entity.from;
		var url = "/mai/#mail_stts=S#lbl_nm="+from+"#mail_seq="+mail_seq+"#";
		location.href = url;
	},
		
	open : this.openEditPopup,
	
	// 작성 윈도우 열기
	openEditPopup : function() {
		var url = "/mai/sync/new.jstl?target=layer";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	// 작성 윈도우 열기
	openEditPopup_reply: function(from,seq) {
		var url = "/mai/sync/new.jstl?mail_seq="+seq+"&from="+from+"&target=layer&orderType=reply";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	openEditPopup_forward: function(from,seq) {
		var url = "/mai/sync/new.jstl?mail_seq="+seq+"&from="+from+"&target=layer&orderType=forward";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	openEditPopup_jnl: function(work_jrnl_seq) {
		var url = "/mai/sync/new.jstl?work_jrnl_seq="+work_jrnl_seq+"&target=layer";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	openEditPopup_approval: function(approval_seq) {
		var url = "/mai/sync/new.jstl?approval_seq="+approval_seq+"&target=layer";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	openEditPopup_approvalExternal: function(approvalExternal_seq) {
		var url = "/mai/sync/new.jstl?approvalExternal_seq="+approvalExternal_seq+"&target=layer";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	openEditPopup_Allreply: function(from,seq){
		var url = "/mai/sync/new.jstl?mail_seq="+seq+"&from="+from+"&target=layer&orderType=replyall";
		layMaiPopup.loadEmbeddedCreationFormContent(url);
	},
	
	// 윈도우 닫기
	close: function() {
		$("#"+layMaiPopup.cnfgWinId).animate({right:-840, opacity:1}, 'fast', 'swing', function() { $("#"+layMaiPopup.cnfgWinId).css("display", "none"); });
	},
	
	loadEmbeddedCreationFormContent : function(url) {
		layMaiPopup.paintCreationForm();
		
		$.ajax({
			type: "get",
			url: url,
			async: false,
			success: function(response) {
				layMaiPopup.paintCreationFormContent(response);
			}
		});
	},
	
	paintCreationForm : function() {
		embeddedManager.appendCustomEmbeddedContentByUrl(layMaiPopup.cnfgWinId, "/mai/api/embedded/form");
	},
		
	paintCreationFormContent : function(contentHtml) {
		$("#layMaiContent").html(contentHtml);
		embeddedManager.showEmbeddedContent(this.cnfgWinId);
		$("#mail_tit").css("width","365px");
	}
	
};
