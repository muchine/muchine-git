//================================================== 
// TabStrip Controller Class   (2013.03.08 - root2)
// param = {
//		instanceName 	: "tabStrip_uri",		// 탭스트립 컨트롤러 인스턴스 변수명
//		wrapperId 	    : "tabStrip_uri",		// 탭스트립을 출력할 랩퍼 Id (html)
//		iframeName	    : "ifmTabConts_test",	// 비활성 상태의 탭 내용을 숨겨(백업) 놓을 iframe Name (Id 아님)
//		homeTabGroup    : "A",					// 탭 그룹 구분 : A(tabIndex-0), B, C, D... Z
//		homeTabIsDelBtn : false,				// 탭에 delete(x) 버튼 표시 여부 - true or false 
//		homeContsType   : "uriFindForm",		// 탭 내용 구분 : ex) 주소록목록 : adrFindForm
//		homeContsUrl    : null,					// 탭 내용을 동적으로 가져올 주소
//		createTabConts_mappingFunc : createTab_contsMappingFunc_restUri
//};
var TabStripCtrl = function(param) {
	
	var _instanceName = null;
	var _wrapperId	  = null;
	var _iframeName   = null;
	var _homeTabGroup = null;
	var _homeTabIsDelBtn = false;
	var _homeContsType= null;
	var _homeContsUrl = null;
	var _createConfig_mappingFunc = null;

	var _isStart	  = false;
	var _oTabStript   = null;		// #tabStrip_restUri object
	var _oIfm 		  = null;		// iframe tabConts object
	var _cnfgArr 	  = [];			// tab Config array = { group, title, type, conts, contsUrl, ctrlCls}
	
	//=== getter & setter
	//--- getter
	this.get_instanceName	= function() { return _instanceName; };
	this.get_wrapperId		= function() { return _wrapperId; };
	this.get_iframeName		= function() { return _iframeName; };
	this.get_homeTabGroup	= function() { return _homeTabGroup; };
	this.get_homeTabIsDelBtn= function() { return _homeTabIsDelBtn; };
	this.get_homeContsType	= function() { return _homeContsType; };
	this.get_homeContsUrl	= function() { return _homeContsUrl; };
	this.get_createConfig_mappingFunc = function() { return _createConfig_mappingFunc; };

	this.get_isStart		= function() { return _isStart; };
	this.get_oTabStript		= function() { return _oTabStript; };
	this.get_oIfm			= function() { return _oIfm; };
	this.get_cnfgArr		= function() { return _cnfgArr; };
	
	//--- setter
	this.set_instanceName	= function(val) { return _instanceName = val; };
	this.set_wrapperId		= function(val) { return _wrapperId = val; };
	this.set_iframeName		= function(val) { return _iframeName = val; };
	this.set_homeTabGroup	= function(val) { return _homeTabGroup = val; };
	this.set_homeTabIsDelBtn= function(val) { return _homeTabIsDelBtn = val; };
	this.set_homeContsType	= function(val) { return _homeContsType = val; };
	this.set_homeContsUrl	= function(val) { return _homeContsUrl = val; };
	this.set_createConfig_mappingFunc = function(val) { return _createConfig_mappingFunc = val; };
	
	this.set_isStart		= function(val) { return _isStart = val; };
	this.set_oTabStript		= function(val) { _oTabStript = val; };
	this.set_oIfm			= function(val) { _oIfm = val; };
	this.set_cnfgArr		= function(val) { _cnfgArr = val; };
	
	if(param) {
		this.start(param);
		this.set_isStart(true);
	}
};

TabStripCtrl.prototype = {
		
	start	: function(param) {
		console.log("TabStripCtrl.start() ================= ");
		
		if(this.get_isStart()== false && param) {
			this.set_instanceName(param.instanceName);
			this.set_wrapperId(param.wrapperId);
			this.set_iframeName(param.iframeName);
			this.set_homeTabGroup( (param.homeTabGroup) ? param.homeTabGroup : "A" );
			this.set_homeTabIsDelBtn( (param.homeTabIsDelBtn) ? true : false );
			this.set_homeContsType(param.homeContsType);
			this.set_homeContsUrl( (param.homeContsUrl) ? param.homeContsUrl : null );
			this.set_createConfig_mappingFunc(param.createTabConts_mappingFunc);
			
			this.set_isStart(true);
		}
		
		var oThis = this;
		var wrapperId = this.get_wrapperId();
		
		$("#"+wrapperId).kendoTabStrip({
			animation:	{
				open: {effects: "fadeIn"}
			},
			select: function(e) {
				oThis.onSelect_tab(e);
			}
		});
		
		this.init();
	},
		
	init	: function() {
		console.log("TabStripCtrl.init() ================= ");
		
		//-- 탭(속성) 생성 함수 오버라이딩
		this.config_create = this.get_createConfig_mappingFunc();
		
		this.set_oTabStript( $("#"+this.get_wrapperId()).data("kendoTabStrip") );
		this.set_oIfm( frames[this.get_iframeName()].document.getElementsByTagName("body") );
		
		/*
		var cnfgArr = this.get_cnfgArr();
		$("li", "#"+this.get_wrapperId()).each(function(idx) {
			var tabCnfg = {group:null, type:null, isDelBtn:false, title:null, conts:"&nbsp;", contsUrl:null, ctrlCls:null};
			var dftCnfg = JSON.parse( $(this).attr("dftCnfg") );
			$.extend(tabCnfg, dftCnfg);
			cnfgArr.push(tabCnfg);
		});
		*/
		
		//-- TabStripCtrl 생성 시 처음으로 표시할 탭내용 지정
		var tabGroup  = this.get_homeTabGroup();
		var isDelBtn  = this.get_homeTabIsDelBtn();
		var contsType = this.get_homeContsType(); 
		var contsUrl  = this.get_homeContsUrl();
		this.config_appendPush(tabGroup, contsType, isDelBtn, contsUrl);
		//this.onOpen_tab(tabGroup, contsType, isDelBtn, contsUrl);
	},
	
	// 탭 제목 태그 생성
	createTag_tabTitle : function(strTitle, cnfg) {
		console.log("TabStripCtrl.createTag_tabTitle(strTitle, cnfg.group, cnfg.type) ================= "+strTitle+", "+cnfg.group+", "+cnfg.type);
		var closeButton = this.createTag_tabCloseButton(cnfg.isDelBtn);
		var tabTitle = "<span class='tabTitle' tabgroup='"+cnfg.group+"' contstype='"+cnfg.type+"'>" + strTitle + closeButton +"</span>";
		
		return tabTitle;
	},
	
	// 탭 close(X) 버튼 태그 생성
	createTag_tabCloseButton : function(isDelBtn) {
		console.log("TabStripCtrl.createTag_tabCloseButton(isDelBtn) ================= "+isDelBtn);
		var closeButton = (isDelBtn == true) ? "&nbsp;&nbsp;&nbsp;<span class='tabCloseBtn' onclick='"+this.get_instanceName()+".onClose_tab(this)'>X</span>" : "";
		
		return closeButton;
	},
	
	// 탭(속성) 객체 생성
	config_create	: function(tabGroup, contsType, isDelBtn) {
		console.log("TabStripCtrl.config_create(tabGroup, contsType) ================= "+tabGroup+", "+contsType);
		
		var tabCnfg = {
			group	: tabGroup,
			type	: contsType,
			isDelBtn: (isDelBtn) ? true : false,
			title	: null,
			conts	: null,
			contsUrl: null,			// tabConts Url  : 해당 url 로 html 내용 가져오기
			ctrlCls	: null			// 해당 탭을 컨트롤하는 인스턴스 객체 : new AdrInfoList()
		};
		
		//======= 이하부터. 사용자 정의	===========================================================
		//-- 예) 생성할 tab mapping 샘플
		if(contsType == "restUri_findForm") {			// ex) adrFindForm 
			tabCnfg.title	 = this.createTag_tabTitle(tabGroup+") List", tabCnfg);
			tabCnfg.contsUrl = gContextPath + "/resources/abc/devRef/ken_tabStrip_examConts_list.jsp";
			tabCnfg.ctrlCls = new RestUriListCls();
		}
		else if(contsType == "restUri_createForm") {	// ex) adrCreateForm
			tabCnfg.title	 = this.createTag_tabTitle(tabGroup+") Edit", tabCnfg);
			tabCnfg.contsUrl = gContextPath + "/resources/abc/devRef/ken_tabStrip_examConts_edit.jsp";
			tabCnfg.ctrlCls  = new RestUriEditCls();
		}
		else if(contsType == "restUri_read") {			// ex) adrRead
			tabCnfg.title	 = this.createTag_tabTitle(tabGroup+") Show", tabCnfg);
			tabCnfg.contsUrl = gContextPath + "/resources/abc/devRef/ken_tabStrip_examConts_show.jsp";
			tabCnfg.ctrlCls  = new RestUriShowCls();
		}
		
		return tabCnfg;
	},
	
	// 탭(속성) 객체 저장, 적용
	config_appendPush	: function(tabGroup, contsType, isDelBtn, contsUrl) {
		console.log("TabStripCtrl.config_appendPush(tabGroup, contsType, isDelBtn, contsUrl) ================= "+tabGroup+", "+contsType+", "+isDelBtn+", "+contsUrl);
		
		var isIndex_both = isIndex_group = isIndex_conts = null;
		
		// 탭그룹 Z 는 탭 중복생성 허용, 나머지는 해당 탭그룹을 찾아서 표시
		if(tabGroup != "Z") {
			// 해당 탭그룹이 열려있고 + 같은 탭내용이 이미 등록되어 있는지 확인
			isIndex_both  = isIndex_group = isIndex_conts = this.is_tabGroup_contsType(tabGroup, contsType);
			
			// 해당 탭그룹이 이미 열려있는지 확인
			isIndex_group = this.is_tabGroup(tabGroup);
			
			// 같은 탭내용이 이미 등록되어 있는지 확인
			isIndex_conts = this.is_contsType(contsType);
		}
		
		console.log("isIndex_both : "+isIndex_both+"\nisIndex_group : "+isIndex_group+"\nisIndex_conts : "+isIndex_conts);
		
		
		//-- 처음 추가하는 탭이라면
		if(isIndex_group == null) {
			
			var cnfg = this.config_create(tabGroup, contsType, isDelBtn);
				cnfg.contsUrl = (contsUrl) ? contsUrl : cnfg.contsUrl;
				console.log("cnfg.contsUrl : "+cnfg.contsUrl);
			
			if(cnfg.conts == null && cnfg.contsUrl) {
				
				var oThis = this;
				
				$.ajax({
					url		: cnfg.contsUrl,
					async	: true,
					cache	: false,
					success : function(htmlText) {
						//console.log("TabStripCtrl.config_appendPush.ajaxSuccess(htmlText) -------\n"+htmlText);
						console.log("TabStripCtrl.config_appendPush.ajaxSuccess(htmlText) -----------------------------------------");
						
						cnfg.conts = htmlText;
						oThis.insert_tabConts(cnfg);
					}
				});
			}
			else {
				this.insert_tabConts(cnfg);				
			}
		}
		//-- 이미 오픈된 탭이 있고,
		else {
			
			// 탭내용을 새로 등록하는 것이라면 -> 탭내용 업데이트(이전내용삭제, 새내용 등록)
			if(isIndex_both == null) {
				var isActive = true; // 업데이트 후 포커스를 줄 것인지 여부 (true || false)
				this.config_updatePush(tabGroup, contsType, isIndex_group, isActive, contsUrl);
			}
			// 이미 탭내용도 등록되어 있다면 -> 탭에 포커스만 주기
			else {
				this.get_oTabStript().select(isIndex_group);	//-> onSelect_tab(e) 핸들러 함수 자동호출
			}
		}
	},
	
	// 해당 탭그룹이 열려있고 + 같은 탭내용이 이미 등록되어 있는지 확인
	is_tabGroup_contsType : function(tabGroup, contsType) {
		console.log("TabStripCtrl.is_tabGroup_contsType(tabGroup, contsType) ================= "+tabGroup+", "+contsType);

		var tabIndex = null;	// 반환값
		var selector = "ul.k-tabstrip-items span.k-link span.tabTitle[tabgroup='" +tabGroup+ "'][contstype='" +contsType+ "']";
		var nodeSpan = $(selector, "#"+this.get_wrapperId()).eq(0);
		
		if(nodeSpan.length) {
			tabIndex = $(nodeSpan).parent().parent().index();	// li.tab 의 index
		}
		/*
		var tabIndex = null;	// 반환값
		var cnfgArr = this.get_cnfgArr();
		for(var i=0; i < cnfgArr.length; i++) {
			if(cnfgArr[i].group == tabGroup && cnfgArr[i].type == contsType) {
				tabIndex = i;
				break;
			}
		}
		*/
		return tabIndex;
	},
	
	// 해당 탭그룹이 이미 열려있는지 확인
	is_tabGroup		: function(tabGroup) {
		console.log("TabStripCtrl.is_tabGroup(tabGroup) ================= "+tabGroup);
		
		var tabIndex = null;	// 반환값
		var selector = "ul.k-tabstrip-items span.k-link span.tabTitle[tabgroup='" +tabGroup+ "']";
		var nodeSpan = $(selector, "#"+this.get_wrapperId()).eq(0);
		
		if(nodeSpan.length) {
			tabIndex = $(nodeSpan).parent().parent().index();	// li.tab 의 index
		}
		/*
		var tabIndex = null;	// 반환값
		var cnfgArr = this.get_cnfgArr();
		for(var i=0; i < cnfgArr.length; i++) {
			if(cnfgArr[i].group == tabGroup) {
				tabIndex = i;
				break;
			}
		}
		*/
		return tabIndex;
	},

	// 같은 탭내용이 이미 등록되어 있는지 확인
	is_contsType	: function(contsType) { 
		console.log("TabStripCtrl.is_contsType(contsType) ================= "+contsType);

		var tabIndex = null;	// 반환값
		var selector = "ul.k-tabstrip-items span.k-link span.tabTitle[contstype='" +contsType+ "']";
		var nodeSpan = $(selector, "#"+this.get_wrapperId()).eq(0);
		
		if(nodeSpan.length) {
			tabIndex = $(nodeSpan).parent().parent().index();	// li.tab 의 index
		}
		
		return tabIndex;
	},
	
	// 탭내용 삽입
	insert_tabConts	: function(cnfg) {
		console.log("TabStripCtrl.insert_tabConts(cnfg) ================= "+cnfg.title);
		
		// 탭 추가
		var oTabStript = this.get_oTabStript();
		oTabStript.append([{
			encoded	:false,
			text	:cnfg.title,
			content	:cnfg.conts 
		}]);
		
		// 탭속성 저장
		this.get_cnfgArr().push(cnfg);
		
		// 추가한 탭내용을 임시 비우기 (oIfm 으로 옮기기) - why? 탭추가와 탭포커스(Active) 의 내용표시 패턴을 통일시키기 위해
		this.hide_tabConts(this.get_cnfgArr().length - 1);
		
		// 추가한 탭 포커스 주기
		// 자동호출되는 onSelect_tab(e) 핸들러 함수에서 oIfm 에 옮겨놨던 탭내용 복구 
		this.get_oTabStript().select(this.get_cnfgArr().length - 1);
	
	},
	
	// 탭내용 비우기(숨기기)
	hide_tabConts	: function(tabIndex) {
		console.log("TabStripCtrl.hide_tabConts(tabIndex) ================= "+tabIndex);
		
		var tabContsBx = this.get_oTabStript().contentElement(tabIndex);	// kendo tabConts 래퍼
		var cnfgArr    = this.get_cnfgArr();
		var cnfg	   = cnfgArr[tabIndex];
		    cnfg.conts = $(tabContsBx).children();
		
		cnfgArr[tabIndex] = cnfg;
		this.set_cnfgArr(cnfgArr);
		
		$(this.get_oIfm()).append(cnfg.conts);	// 탭내용을 iframe 으로 이동, 백업
	},
	
	// 탭내용 보이기
	display_tabConts: function(tabIndex) {
		console.log("TabStripCtrl.display_tabConts(tabIndex) ================= "+tabIndex);
		
		var cnfg	   = this.get_cnfgArr()[tabIndex];
		console.log(cnfg.conts);
		var tabContsBx = this.get_oTabStript().contentElement(tabIndex);	// kendo tabConts 래퍼
		console.log(tabContsBx);
		$(tabContsBx).append(cnfg.conts);	// 탭내용을 iframe 에서 가져와서 복원
	},
	
	//탭속성 업데이트 하기 (이전내용삭제, 새내용 리로드) - isActive : 업데이트 후 포커스를 줄 것인지 여부 (true || false)
	config_updatePush	: function(tabGroup, contsType, tabIndex, isActive, contsUrl) {
		console.log("TabStripCtrl.config_updatePush(tabGroup, contsType, tabIndex, isActive, contsUrl) ================= "+tabGroup+", "+contsType+", "+tabIndex+", "+isActive+", "+contsUrl);
		//--- 탭내용별 예외처리 추가
		// if(contsType == "adrFindForm") {
		//	 this.gridPageNo = 1;
		// }
		
		var cnfg	 = this.get_cnfgArr()[tabIndex];
		var isDelBtn = cnfg.isDelBtn;
		
		var new_cnfg = this.config_create(tabGroup, contsType, isDelBtn);
		    new_cnfg.contsUrl = (contsUrl) ? contsUrl : new_cnfg.contsUrl;
		
		if(new_cnfg.conts == null && new_cnfg.contsUrl) {
			
			var oThis = this;
			
			$.ajax({
				url		: new_cnfg.contsUrl,
				async	: false,
				cache	: false,
				success : function(htmlText) {	// console.log("TabStripCtrl.config_updatePush.ajaxSuccess(htmlText) -------\n"+htmlText);
					new_cnfg.conts = htmlText;
					oThis.update_tabConts(new_cnfg, tabIndex, isActive);
				}
			});
		}
		else {
			this.update_tabConts(new_cnfg, tabIndex, isActive);
		}
	},
	
	//탭내용 업데이트(리로드 하기) - isActive : 업데이트 후 포커스를 줄 것인지 여부 (true || false) 
	update_tabConts : function(new_cnfg, tabIndex, isActive) {
		console.log("TabStripCtrl.update_tabConts(new_cnfg, tabIndex, isActive) ================= "+new_cnfg+", "+tabIndex+", "+isActive);
		
		var tabContsBx = this.get_oTabStript().contentElement(tabIndex);	// kendo tabConts 래퍼
		
		$(tabContsBx).empty().html(new_cnfg.conts);

		// onReady() 실행	: 처음 로드되었을 때 한 번만 실행
		if(new_cnfg.ctrlCls && new_cnfg.ctrlCls.onReady && new_cnfg.ctrlCls.get_isReady() == false) {
			console.log("update_tabCons->onReady() 호출");
			new_cnfg.ctrlCls.onReady();
		}
		
		// onFocus() 실행 : 포커스 받을 때 마다 실행
		if(new_cnfg.ctrlCls && new_cnfg.ctrlCls.onFocus) {
			console.log("update_tabCons->onFocus() 호출");
			new_cnfg.ctrlCls.onFocus();
		}

		// onReady + onFocus 실행한 내용 저장
		new_cnfg.conts =$(tabContsBx).children();
		var cnfgArr = this.get_cnfgArr();
		cnfgArr[tabIndex] = new_cnfg;
		this.set_cnfgArr(cnfgArr);
				
		var activeIndex = this.get_activeIndex();
		console.log(activeIndex+" : "+tabIndex);
		
		// 업데이트한 탭에 포커스 주기
		if(isActive == true && activeIndex != tabIndex) {
			
			this.hide_tabConts(tabIndex);			// 변경한 탭내용을 임시 비우기 (oIfm 으로 옮기기)
			this.get_oTabStript().select(tabIndex);	// 추가한 탭에 포커스 주기
													// 자동으로 onSelect_tab(e) 핸들러 함수 호출 -> oIfm 에 옮겨놨던 탭내용 복구 됨
		}
		else {
			this.hide_tabConts(tabIndex);
			this.display_tabConts(tabIndex);
		}

		// 탭제목 업데이트
		this.update_tabTitle(tabIndex, new_cnfg.title);
	},
	
	// 탭제목 업데이트
	update_tabTitle : function(tabIndex, tabTitle) {
		console.log("TabStripCtrl.update_tabTitle(tabIndex, tabTitle) ================= "+tabIndex+", "+tabTitle);
		
		var liSelector = "ul.k-tabstrip-items li.k-item";
		var liNode   = $(liSelector, "#"+this.get_wrapperId()).eq(tabIndex);
		var spanNode = $("span.k-link", liNode).eq(0);	// <span class="k-link">탭제목</span>
		    spanNode.html(tabTitle);
	},
	
	// 탭내용 삭제
	delete_tabConts	: function(tabIndex) {
		console.log("TabStripCtrl.delete_tabConts(tabIndex) ================= "+tabIndex);
		
		this.get_oTabStript().remove(tabIndex);
		
		var cnfgArr = this.get_cnfgArr();
		cnfgArr.removeByIndex(tabIndex);
		this.set_cnfgArr(cnfgArr);
		
		this.get_oTabStript().select(0);				// 첫번째 탭에 포커스 주기 : onSelect_tab(e) 자동 호출됨
	},
	
	// 현재 포커스를 가지고 있는 탭 index 얻기
	get_activeIndex	: function() {
		console.log("TabStripCtrl.get_activeIndex() ================= ");
		
		var activeIndex = $('ul.k-tabstrip-items>li.k-state-active', '#'+this.get_wrapperId()).index();
		return activeIndex;
	},
	
	// 지정 탭(인덱스)의 탭그룹값 얻기
	get_tabGroupByIndex : function(tabIndex) {
		console.log("TabStripCtrl.get_tabGroup(tabIndex) ================= "+tabIndex);
		
		var cnfg	 = this.get_cnfgArr()[tabIndex];
		var tabGroup = cnfg.group;

		/* 노드 접근방식으로 탭그룹값 얻기
		var liSelector = "ul.k-tabstrip-items li.k-item";
		var liNode   = $(liSelector, "#"+this.wrapperId).eq(tabIndex);
		var spanNode = $("span.k-link span", liNode).eq(0);	// <span onclick="tabStripCtrl_instance.onClose_tab(this)" contstype="testFindForm" tabgroup="B">X</span>
		var tabGroup = $(spanNode).attr("tabgroup");
		*/
		console.log("tabGroup : "+tabGroup);
		
		return tabGroup;
	},
	
	//=== e핸들러	=======================================
	// 탭 선택
	onSelect_tab		: function(e) {
		console.log("TabStripCtrl.onSelect_tab() ================= ");
		
		var tabContsBx = null;
		
		var cur_index = this.get_activeIndex();
		var new_index = $(e.item).closest("li").index();
		//console.log("cur_index : "+cur_index+"\nnew_index : "+new_index);

		// 현재 선택되어 있는 탭내용 숨기기
		if(cur_index >= 0 && cur_index != new_index)
			this.hide_tabConts(cur_index);
		
		// 새로 선택할(포커스 줄) 탭내용 보이기 -> iframe 에서 백업해둔 내용 가져와 다시 보이기
		var new_cnfg = this.get_cnfgArr()[new_index];
		this.display_tabConts(new_index);
		
		// onReady() 실행	: 처음 로드되었을 때 한 번만 실행
		if(new_cnfg.ctrlCls && new_cnfg.ctrlCls.onReady && new_cnfg.ctrlCls.get_isReady() == false) {
			new_cnfg.ctrlCls.onReady();
		}
		
		// onFocus() 실행 : 포커스 받을 때 마다 실행
		if(new_cnfg.ctrlCls && new_cnfg.ctrlCls.onFocus) {
			new_cnfg.ctrlCls.onFocus();
		}
		
		// onReady + onFocus 실행한 내용 저장
		tabContsBx = this.get_oTabStript().contentElement(new_index);
		new_cnfg.conts = $(tabContsBx).children();
		
		var cnfgArr = this.get_cnfgArr();
		cnfgArr[new_index] = new_cnfg;
		this.set_cnfgArr(cnfgArr);
	},
	
	// 탭 열기(추가)
	onOpen_tab		: function(tabGroup, contsType, isDelBtn, contsUrl) {
		console.log("TabStripCtrl.onOpen_tab(tabGroup, contsType, contsUrl) ================= "+tabGroup+", "+contsType+", "+contsUrl);
		
		this.config_appendPush(tabGroup, contsType, isDelBtn, contsUrl);
	},
	
	// 탭 닫기(삭제)
	onClose_tab		: function(eTarget) {
		console.log("TabStripCtrl.onClose_tab(eTarget) ================= ");
		
		// eTarget(<span>) 이 없으면, 현재 active 상태의 탭을 닫는다.
		var del_index = (eTarget) ? $(eTarget).closest("li").index() : this.get_activeIndex();
		this.delete_tabConts(del_index);
		console.log("del_index : "+del_index);
	}
};

var TabManager = {
	
	paint : function(tabId) {
		$("#" + tabId).kendoTabStrip({
			animation:	{
				open: {effects: "fadeIn"}
			}
		});
	}
	
};

var TabMapper = {
		
	maps : function(tabstrip, mappings, tabGroup, contsType, isDelBtn) {
		var tab = {
			group	: tabGroup,		// 탭 그룹 구분값
			type	: contsType,	// 탭 내용 구분값
			isDelBtn: (isDelBtn) ? true : false,
			title	: null,			// 탭 제목
			conts	: null,			// 탭 내용
			contsUrl: null,			// tabConts Url  : 해당 url 로 html 내용 가져오기
			ctrlCls	: null			// 탭 내용에 해당하는 뷰 페이지를 컨트롤하는 객체 : new AdrInfoList()
		};
		
		var self = this;
		
		for (var i = 0; i < mappings.length; i++) {
			if (mappings[i] == undefined || contsType != mappings[i].type) continue;

			tab.title	 = tabstrip.createTag_tabTitle(mappings[i].name, tab);
			tab.contsUrl = mappings[i].url;
			tab.ctrlCls  = new TabComponent(tabstrip, mappings[i].component);
			break;
		}
		
		return tab;
	}

};

/**
 * Tab handler should implement ready() and focused() methods.
 */
var TabComponent = function(tabStripCtrl, tabHandler) {

	var _tabHandler		= tabHandler;
	var _tabStripCtrl	= tabStripCtrl;
	var _isReady		= false;

	this.get_tabStripCtrl	= function() { return _tabStripCtrl; };
	this.get_isReady		= function() { return _isReady; };
	this.tabHandler			= function() { return _tabHandler; };

	this.set_isReady		= function(val) { _isReady = val; };
};

TabComponent.prototype = {

	//-- 탭에 로드시 처음 한 번 만 실행할 내용 기술
	onReady		: function() {
		this.tabHandler().tabStripCtrl = this.get_tabStripCtrl();
		this.tabHandler().ready();

		this.set_isReady(true);
	},

	//-- 탭이 선택될(포커스) 마다 실행해야 하는 내용 기술
	onFocus		: function() {
		this.tabHandler().focused();
	}
};
