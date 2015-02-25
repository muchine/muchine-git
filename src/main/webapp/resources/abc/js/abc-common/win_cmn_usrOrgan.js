//================================================== 
// WinOrganCls Class   (2013.03.28 - root2)
// 공용(일반) 조직도 윈도우

var WinOrganCls = function(params) {

	if(window.com_abc_winCmn_usrOrgan_counter == 0 || window.com_abc_winCmn_usrOrgan_counter > 0)
		window.com_abc_winCmn_usrOrgan_counter++;
	else
		window.com_abc_winCmn_usrOrgan_counter = 0;
	
	var _namespaceWinId			= "winCls_organ_";
	var _winNo					= window.com_abc_winCmn_usrOrgan_counter;					// static variable, window key number
	var _winId					= (params.wrapperId) ? params.wrapperId : null;
	var _winTitle				= (params.title) ? params.title : "조직도 윈도우";
	var _allowDupli				= (params.allowDupli == true) ? true : false;				// 노드선택 중복허용 여부
	var _allowNode				= (params.allowNode) ? params.allowNode : "dept_grp_user";	// 선택추가 허용노드 (all, corp_dept_grp_user, corp_dept_user, grp_user)
	var _allowUserSelf			= (params.allowUserSelf == true) ? true : false;			// 사용자 본인 선택허용 여부
	// var _allowUserGrp			= (params.allowUserGrp == false) ? false : true;			// 내그룹사용여부(default:true)
	var _allowUserGrp			= (typeof params.allowUserGrp == "undefined") ? false : params.allowUserGrp;			// 내그룹사용여부(default:false)
	var _displayNode			= (params.displayNode) ? params.displayNode : 'all';		// 조직도에 출력할 노드
	var _maxSelectCnt			= (params.maxSelectCnt > 0) ? params.maxSelectCnt : 0;		// 선택가능한 최대 노드 수
	var _call_onApply_winBtn	= (params.onApply_winBtn) ? params.onApply_winBtn : null;
	
	var _winObj					= null;
	var _oTabStrip				= null;			// kendo-tabStrip 객체 : 조직도, 검색 탭스트립
	var _oSearchTree			= null;			// kendo-treeView 객체 : 검색결과 트리
	var _instance_organTree		= null;
	var _instance_groupTree		= null;
	var _instance_selectList	= null;
	

	//=== getter & setter
	//--- getter
	this.get_namespaceWinId		= function() { return _namespaceWinId; };
	this.get_winNo				= function() { return _winNo; };
	this.get_winId				= function() { return _winId; };
	this.get_winTitle			= function() { return _winTitle; };
	this.get_allowDupli 		= function() { return _allowDupli; };
	this.get_allowNode			= function() { return _allowNode; };
	this.get_allowUserSelf		= function() { return _allowUserSelf; };
	this.get_allowUserGrp		= function() { return _allowUserGrp; };
	this.get_displayNode		= function() { return _displayNode; };
	this.get_maxSelectCnt		= function() { return _maxSelectCnt; };
	this.get_call_onApply_winBtn= function() { return _call_onApply_winBtn; };
	this.get_winObj				= function() { return _winObj; };
	this.get_oTabStrip			= function() { return _oTabStrip; };
	this.get_oSearchTree		= function() { return _oSearchTree; };
	this.get_instance_organTree	= function() { return _instance_organTree; };
	this.get_instance_groupTree	= function() { return _instance_groupTree; };
	this.get_instance_selectList= function() { return _instance_selectList; };
	
	//--- setter
	this.set_namespaceWinId		= function(val) { _namespaceWinId = val; };
	this.set_winId				= function(val) { _winId = val; };
	this.set_winTitle			= function(val) { _winTitle = val; };
	this.set_allowNode			= function(val) { _allowNode = val; };
	this.set_allowUserSelf		= function(val) { _allowUserSelf = val; };
	this.set_allowUserGrp		= function(val) { _allowUserGrp = val; };
	this.set_displayNode		= function(val) { _displayNode = val; };
	this.set_maxSelectCnt		= function(val) { _maxSelectCnt = val; };
	this.set_call_onApply_winBtn= function(val) { _call_onApply_winBtn = val; };
	this.set_winObj				= function(val) { _winObj = val; };
	this.set_oTabStrip			= function(val) { _oTabStrip = val; };
	this.set_oSearchTree		= function(val) { _oSearchTree = val; };
	this.set_instance_organTree	= function(val) { _instance_organTree = val; };
	this.set_instance_groupTree	= function(val) { _instance_groupTree = val; };
	this.set_instance_selectList= function(val) { _instance_selectList = val; };

	// _winId 로 kendoWindow 가 계속 만들어지는 현상 때문에 이미 있으면 강제로 삭제함
	var alreadyKendoWindow = $("div[id=" + _winId + "]:eq(1)").data("kendoWindow"); // _winId 의 kendoWindow 변수
	if (typeof (alreadyKendoWindow) != "undefined") { // _winId 로 이미 kendoWindow 가 있으면
		alreadyKendoWindow.destroy(); // _winId 의 kendoWindow 삭제
	}

	// WinOrganCls Constructor
	if(_winId != null)
		this.init_window();
};

WinOrganCls.prototype = {
		
	init_window	: function() {
		
		var oThis = this;
		
		//=== 윈도우 박스		=================================
		// 윈도우 html 마크업 세팅
		this.appendHtml_wrapperChild();
		
		//var subMenuConts = $("#win_organ").html();
		//$("#windowId").html(subMenuConts);
		//$("#win_organ").remove();


		// kendoWindow 세팅
		var wrapperId 	= this.get_winId();
		var winObj = $("#"+wrapperId);
		winObj.kendoWindow({
			width	: "700px",
			height	:"430px",
			title	: this.get_winTitle(),
			modal	:true
	     });
		this.set_winObj(winObj);
		
		
		//=== 왼쪽 박스			================================= 
		// 탭스크립 세팅
		$("#"+this.get_namespaceWinId()+this.get_winNo()).kendoTabStrip({
			animation:	{
				open: {duration: 0, effects: "fadeIn"}
			}
		});
		this.set_oTabStrip( $("#"+this.get_namespaceWinId()+this.get_winNo()).data("kendoTabStrip") );
		
		// 조직도 데이터 로딩 & treeView 세팅
		var usrOrganCls = new UsrOrganCls("#win_organ_tree_"+this.get_winNo(), function(dataItem){
			oThis.call_onClick_organTree(dataItem);}, 
			this.get_displayNode()
		);
		this.set_instance_organTree(usrOrganCls);
		
		// 내그룹 데이터 로딩 & treeView 세팅
		if(this.get_allowUserGrp())
		{
			var usrGroupCls = new UsrGroupCls("#win_group_tree_"+this.get_winNo(), function(dataItem){
				oThis.call_onClick_organTree(dataItem);}, 
				this.get_displayNode()
			);
			this.set_instance_groupTree(usrGroupCls);
		}
		
		// 검색버튼
		$(".win_organ_search_btn", "#"+this.get_winId()).each(function(index) {
			$(this).live("click", function(){oThis.onSearch();});
		});
		
		
		//=== 오른쪽 박스		================================= 
		// 셀렉트리스트 세팅
		var params = {
			wrapId 		: "win_organ_selectList_"+this.get_winNo(),		// 필수 - selectList를 출력할 랩퍼 Id (html)
			width  		: 250,								// 선택 - 랩퍼 너비 (default : 100, 최소값 100)
			height  	: 250,								// 선택 - 랩퍼 높이 (default : 150, 최소값 150)
			allowDupli	: this.get_allowDupli(),			// 선택 - 노드 중복선택(등록) 허용 여부
			allowNode	: this.get_allowNode(),				// 선택 - 노드 선택시 등록을 허용할 노드타입 (default:deptuser) -> all, corpdeptuser, corpdept, corpuser, deptuser, dept, user)
		};
		var selectListCls = new SelectListCls(params);
		this.set_instance_selectList(selectListCls);
		
		//=== 아래 박스(버튼)	=================================
		// 설정버튼
		$(".win_organ_btnBx button", "#"+this.get_winId()).each(function(index) {
			if(index == 0)
				$(this).live("click", function(){oThis.onApply();});
			else if(index == 1)
				$(this).live("click", function(){oThis.onClose();});
		});
		
		//this.get_winObj().data("kendoWindow").center();
	},
	
	// 윈도우 내부의 html 요소 삽입
	appendHtml_wrapperChild : function() {
		console.log("WinOrganCls.appendHtml_wrapperChild() ================= ");
		
		var oThis		= this;
		var namespaceWinId = this.get_namespaceWinId();
		var winNo		= this.get_winNo();
		var wrapperId 	= this.get_winId();
		var wrapper		= $("#"+wrapperId).addClass("win_organ");
		
		// 왼쪽 박스 : 탭스트립
		var div_win_leftBx = $("<div></div>").addClass("win_leftBx");
		var leftBx_tabStrip = $("<div></div>").attr({"id":namespaceWinId+winNo, "class":"win_organ_tabStrip"});
		var leftBx_tabStrip_ul = $("<ul></ul>");
		var leftBx_tabStrip_li_0 = $("<li></li>").addClass("k-state-active").text("조직도");
		var leftBx_tabStrip_li_1 = $("<li></li>").text("내그룹");
		var leftBx_tabStrip_li_2 = $("<li></li>").text("검색");
		
		// 조직도
		var leftBx_tabStrip_organTree = $("<div></div>").append( $("<div></div>").attr({"id":"win_organ_tree_"+winNo, "class":"win_organ_tree"}) );
		// 내그룹
		if(this.get_allowUserGrp())
		{
			var leftBx_tabStrip_groupTree = $("<div></div>").append( $("<div></div>").attr({"id":"win_group_tree_"+winNo, "class":"win_organ_tree"}) );
		}
		// 검색
		var leftBx_tabStrip_search = $("<div></div>");
		var leftBx_tabStrip_search_input = $("<input />").attr({"type":"text", "id":"win_organ_search_txt_"+winNo, "class":"k-textbox win_organ_search_txt", "value":""});
			$(leftBx_tabStrip_search_input).on("click", function(evt) {
				var e = evt || window.event;
				var eTarget = e.target || e.srcElement;
				$(eTarget).css("imeMode", "active");	// IE-한영변환오류처리 : 기본 한글로 지정
				$("body").trigger("click");				// 포커스 이동을 한 번 시켜줘야 적용된다.
				$(eTarget).focus();
			});
			$(leftBx_tabStrip_search_input).on("keydown", function(evt) {
				var e = evt || window.event;
				if(e.which == 13) {
					oThis.onSearch();
					$ogs.stopBubble(e);
					$ogs.preventDefault(e);
					return false;
				}
			});
		var leftBx_tabStrip_search_button= $("<button></button>").addClass("k-button win_organ_search_btn").text("검색");
		var leftBx_tabStrip_search_div   = $("<div></div>").attr({"id":"win_organ_search_"+winNo, "class":"win_organ_search"});
		    leftBx_tabStrip_search.append(leftBx_tabStrip_search_input).append(leftBx_tabStrip_search_button).append(leftBx_tabStrip_search_div);
		
		if(this.get_allowUserGrp())
		{
			leftBx_tabStrip_ul = $(leftBx_tabStrip_ul).append(leftBx_tabStrip_li_0).append(leftBx_tabStrip_li_1).append(leftBx_tabStrip_li_2);
			leftBx_tabStrip	   = $(leftBx_tabStrip).append(leftBx_tabStrip_ul).append(leftBx_tabStrip_organTree).append(leftBx_tabStrip_groupTree).append(leftBx_tabStrip_search);
		}
		else
		{
			leftBx_tabStrip_ul = $(leftBx_tabStrip_ul).append(leftBx_tabStrip_li_0).append(leftBx_tabStrip_li_2);
			leftBx_tabStrip	   = $(leftBx_tabStrip).append(leftBx_tabStrip_ul).append(leftBx_tabStrip_organTree).append(leftBx_tabStrip_search);
		}
		div_win_leftBx = $(div_win_leftBx).append(leftBx_tabStrip);
		
		// 왼쪽아래 : 아이콘설명(부서장, 접수담당자)
		var div_win_bottomBx = $("<div></div>").addClass("textbullet win_bottomBx");
		var img_1 = $("<img />").addClass("k-image").attr("src", "/resources/abc/images/ico_user1.png");	// 부서장
		var em_1  = $("<em></em>").append(img_1).append(" 부서장&nbsp;&nbsp;");
		var img_2 = $("<img />").addClass("k-image").attr("src", "/resources/abc/images/ico_user2.png");	// 접수담당자
		var em_2  = $("<em></em>").append(img_2).append(" 접수담당자&nbsp;&nbsp;");
		var img_3 = $("<img />").addClass("k-image").attr("src", "/resources/abc/images/ico_user3.png");	// 근태관리자
		var em_3  = $("<em></em>").append(img_3).append(" 근태관리자&nbsp;&nbsp;");
		var img_4 = $("<img />").addClass("k-image").attr("src", "/resources/abc/images/ico_user4.png");	// 결재문서함관리자
		var em_4  = $("<em></em>").append(img_4).append(" 결재문서함관리자");
		div_win_bottomBx = $(div_win_bottomBx).append(em_1).append(em_2).append(em_3).append(em_4);

		// 오른쪽 박스 : 셀렉트 리스트
		var div_win_rightBx = $("<div></div>").addClass("win_rightBx");
		var rightBx_header = $("<header></header>");
		var rightBx_header_h4 = $("<h4></h4>").html("총 <span class='counter'>0</span> 명 선택");
		var rightBx_selectList = $("<div></div>").attr({"id":"win_organ_selectList_"+winNo, "class":"win_organ_selectList"});
		
		rightBx_header = $(rightBx_header).append(rightBx_header_h4);
		div_win_rightBx = $(div_win_rightBx).append(rightBx_header).append(rightBx_selectList);
		
		// 하단 버튼박스
		var div_win_btnBx = $("<div></div>").addClass("win_organ_btnBx");
		var btnBx_btn_0 = $("<button></button>").addClass("k-button").text("적용");
		var btnBx_space = "&nbsp;&nbsp;";
		var btnBx_btn_1 = $("<button></button>").addClass("k-button").text("취소");
		div_win_btnBx = $(div_win_btnBx).append(btnBx_btn_0).append(btnBx_space).append(btnBx_btn_1);
		
		// 하단 안내문
		//var info_b = $("<b></b>").addClass("F_12_black_n").text("※ 더블 클릭으로도 삭제할 수 있습니다.");
		var info_b = $("<b></b>").addClass("F_12_black_n").text("");
		
		// 윈도우 
		$(wrapper).append(div_win_leftBx).append(div_win_bottomBx).append(div_win_rightBx).append(div_win_btnBx).append(info_b);
	},

	// 검색결과 출력
	reflesh_searchTree : function(dataSrc) {
		console.log("WinOrganCls.reflesh_searchTree(dataSrc) ================= "+dataSrc.length);
;
		var oThis = this;
		var searchTreeBxId = "win_organ_search_"+oThis.get_winNo();
		
		//20131119 사용자 표시 설정적용 user/duty/dept user/position/dept
		var user_show_config = "";
		if(User.get("usr_show_cnfg") == "user/duty/dept"){
			user_show_config = "#= item.kor_nm ##=(item.duty_nm)?'/'+item.duty_nm:''##= (item.dept_nm)?'/'+item.dept_nm:'' # ";
		}else if(User.get("usr_show_cnfg") == "user/position/dept"){
			user_show_config = "#= item.kor_nm ##=(item.pos_nm)?'/'+item.pos_nm:''##= (item.dept_nm)?'/'+item.dept_nm:'' # ";
		}
		
		if(this.get_oSearchTree()) {
			$("#"+searchTreeBxId).data("kendoTreeView").setDataSource(dataSrc);
		}
		else {
			$("#"+searchTreeBxId).kendoTreeView({
				dataSource	: dataSrc,
				dataTextField: "nodeName",
				//template: user_show_config,
				select		: function(e) {
					var dataItem = this.dataItem(e.node);
					oThis.call_onClick_organTree(dataItem);
					//alert("dataItem.nodeName : "+dataItem.nodeName);
				}
			});
			var oSearchTree = $("#"+searchTreeBxId).data("kendoTreeView");
			oSearchTree.expand(".k-item");
			this.set_oSearchTree(oSearchTree);
			abcKendoTreeView.setSelectedNodeRemoveClass(searchTreeBxId); // 트리에서 이미 선택된 노드 재선택 가능하도록 세팅
		}
	},
	
	// 조직도 + 검색결과 Tree 의 노드 클릭시 실행할 함수
	call_onClick_organTree : function(dataItem) {
		// 본인선택 불가
		if(dataItem.nodeType == "virt"){   		//가상부서 일때 
			return false;
		}
		if(dataItem.nodeType == "user" && this.get_allowUserSelf() == false) {
			if(dataItem.nodeKey == gUsr_key)
				return false;
		}
		
		if(this.get_allowNode() == "all" || this.get_allowNode().indexOf(dataItem.nodeType) > -1) {
			var instance_selectList = this.get_instance_selectList();
			var curDataArr = instance_selectList.get_dataArr_selectList();
			
			// 선택된 노드수가 허용최대수 일 때는 최상위 노드를 삭제후 새로 선택한 노드를 추가한다.
			if(this.get_maxSelectCnt() > 0 && this.get_maxSelectCnt() == curDataArr.length) {
				var oSelectorUl = instance_selectList.get_oSelectorUl();
				var topNode = $("li", oSelectorUl)[0];
				$(topNode).remove();
				
				// 카운터 감소시키기
				var oCounter = instance_selectList.get_oCounter();
				var cnt = $(oCounter).text();
				$(oCounter).text(--cnt);
			}
			
			instance_selectList.onAdd_organNode(dataItem);
		}
	},
	
	// 조직도 treeView - 아무것도 선택 안 된 상태로 만들기
	clear_selectedNode_treeView : function() {
		//this.get_instance_organTree().get_organTree().select($());	// api 안 먹음.
		var treeBxId = this.get_instance_organTree().get_treeBxId();
		var node = $(treeBxId+" li[aria-selected=true]");
		$(node).attr("aria-selected", "false");
		$("span", node).removeClass("k-state-selected");
		
		// 검색트리
		var searchTreeBxId = "#win_organ_search_"+this.get_winNo();
		node = $(searchTreeBxId+" li[aria-selected=true]");
		$(node).attr("aria-selected", "false");
		$("span", node).removeClass("k-state-selected");
		$("#win_organ_search_txt_"+this.get_winNo()).val("");		// 이전 입력 텍스트 지우기
		this.reflesh_searchTree([]);
	},

	onOpen	: function(params) {
		
		// 오픈시, 항상 조직도 탭내용을 보여주기
		this.get_oTabStrip().select(0);
		
		// 조직도에서 이전 선택했던 노드 비활성화 시키지 (아무것도 선택 안 된 상태로 만들기)
		this.clear_selectedNode_treeView();
		
		// selectList 의 이전 내용 비우기
		var counterText = 0;
		var instance_selectList = this.get_instance_selectList();
		var oSelectUl = instance_selectList.get_oSelectorUl();
		$(oSelectUl).empty();
		
		// 오픈시 selectList 에 표시할 기본 데이터가 있다면, 추가 
		if(params && params.dataArr && params.dataArr.constructor == Array && params.dataArr.length > 0) {
			
			var dataItem = null;
			for(var i=0; i < params.dataArr.length; i++) {
				if(! params.dataArr[i] || params.dataArr[i].constructor != Object) { continue; }
				
				dataItem = params.dataArr[i];
				if(instance_selectList.onAdd_organNode(dataItem)) {
					counterText++;
				};
			}
		}
		console.log("counterText : "+counterText);
		var oCounter = instance_selectList.get_oCounter();
		$(oCounter).text(counterText);
		
		this.get_winObj().data("kendoWindow").center();
		this.get_winObj().data("kendoWindow").open();
		

		//-- 대표부서에 스크롤위치 맞추기
		var treeBxId = "#win_organ_tree_"+this.get_winNo();
		//$(treeBxId).css("height", "150px"); // 테스트 위해서 높이 임시줄임
		setTimeout(
			function() {
				var outBx_height = parseInt($(treeBxId).css("height"), 10);
				var inBx_height  = parseInt($(treeBxId+" > ul.k-group").css("height"), 10);
				
				var minus_cnt = 1;	// '-' 펼쳐진 노드 찾기
				$(treeBxId+" span.k-minus").each(function(evt) {
					if(minus_cnt == 1) { minus_cnt++; }	// 회사노드
					else if(minus_cnt == 2) {			// 대표부서노드
						minus_cnt++;
						//$(this).css("background-color", "red");
						
						var scrollMax = inBx_height - outBx_height;
						var outBx = $(treeBxId).offset();
						var focusNode = $(this).offset();
						
						if(scrollMax > 10) {
							var offsetCha = focusNode.top - outBx.top;
							var focusCha  = offsetCha - outBx_height;
							var focusTop  = focusCha  + outBx_height;
							
							var scrollTop = Math.min(focusTop, scrollMax);
							$(treeBxId).animate({'scrollTop':scrollTop},100);
							//console.log("scrollMax : "+scrollMax);
							//console.log("focusTop : "+focusTop);
							//console.log("scrollTop : "+scrollTop);
						}
					}
				});
			},
			300
		);	// end.setTimeout
	},
	
	// 윈도우 닫기
	onClose : function() {
		this.get_winObj().data("kendoWindow").close();
	},
	
	// 검색 버튼
	onSearch : function() {
		var oThis = this;
		var searchTxt= $("#win_organ_search_txt_"+this.get_winNo()).val().trim();
		
		if(searchTxt) {
			
			$.ajax({	  // load - list.json
				url		: gContextPath + '/usr/info.json',
				data	: abcKendoData.getData({
					// "suggest" : encodeURIComponent(searchTxt),
					"suggest" : searchTxt,
					"model_query_pageable.sortOrders" : abcKendoData.getSortOrders.getUsrInfoSearch()
				}),
				async	: false,
				success : function(jsonData) {
					// console.log("WinOrganCls.onSearch.success() ==================== "+jsonData.length+"\n"+JSON.stringify(jsonData));
					if(! jsonData) { return; }
					var content = jsonData.content;
					var dataArr = [];
					if(content.constructor == Array && content.length > 0) {
						dataArr = content;
					}
					else if(content.constructor == Object) {
						dataArr.push(content);
					}
					var dataItem = null;
					for(var i=0; i < dataArr.length; i++) {
						if(! dataArr[i] || dataArr[i].constructor != Object) { continue; }
						
						dataItem = dataArr[i];
						dataItem.nodeType = "user";
						dataItem.nodeKey  = dataItem.usr_key;
						
						//20140501 사용자 표시 설정적용
						dataItem.nodeName = abcUIUtil.getUserShow(dataItem, dataItem.kor_nm, dataItem.pos_nm, dataItem.duty_nm, dataItem.dept_nm)
						
					}
					
					oThis.reflesh_searchTree(dataArr);
				} // end.success
			}); // end.ajax
		} // end.if(! searchTxt)
	},
	
	// 적용 버튼
	onApply	: function() {
		var instance_selectList = this.get_instance_selectList();
		var dataArr  = instance_selectList.get_dataArr_selectList();
		this.onClose();
		
		// 콜백 호출
		var callBack = this.get_call_onApply_winBtn();
		
		if(typeof callBack === "function")
			callBack(dataArr);
		else if(callBack.constructor == String) {
			eval(callBack)(dataArr);
		}
	},
	
	// 저장 버튼
	onSave	: function() {
		
	}
};

/**
 * 신규 조직도 윈도우 ... added by Sejoon at 2014.01.15
 * @param ui.windowId 조직도가 오픈되는 윈도우 개체 ID
 * @param ui.title 조직도 윈도우 제목
 * @param node.types 선택 가능한 노드 타입. corp, dept, grp, user 중 1개 이상 설정. 설정된 값이 없으면 dept, grp, user가 기본으로 설정됨
 * @param node.myself 자기 자신을 선택할 수 있는지 여부
 * @param transport.option 조직도 표시 옵션. OrganizationTreeDisplayer 클래스 내용 참조 (생략 가능)
 * @param selection.type default이면 기본 리스트 선택 프레임, privilege는 권한 설정 프레임 (생략 가능. 기본값은 default)
 * @param callback 조직도 노드 선택 시 호출될 callback 함수
 */
var OrganizationWindow = function(params) {
	var context				= params;
	var selector			= null;

	if(window.organizationWindowCounter == 0 || window.organizationWindowCounter > 0)
		window.organizationWindowCounter++;
	else
		window.organizationWindowCounter = 0;
	
	context.windowNo = window.organizationWindowCounter;

	if (!context.transport) context["transport"] = {option : ""};
	if (!context.selection) context["selection"] = {type : "default"};
	
	this.context			= function() { return context; };	
	this.selector			= function() { return selector; };
	
	this.setSelector		= function(val) { selector = val; }; 

	this.init();
};

OrganizationWindow.prototype = {
	init : function() {
		var self = this;
		
		var organization = new UserOrganization({
			treeId: "win_organ_tree_" + this.context().windowNo,
			options: this.context().transport.option,
			callback: function(node) {
				self.addNode(node);
			}
		});
		
		var group = new UserGroup({
			treeId	 : "win_group_tree_" + this.context().windowNo,
			enabled	 : this.canSupportGroup(),
			callback: function(node) {
				self.addNode(node);
			}
		});
		
		var search = new UserSearch({
			treeId : "win_organ_search_" + this.context().windowNo,
			textId : "win_organ_search_txt_" + this.context().windowNo,
			callback: function(node) {
				self.addNode(node);
			}
		});
		
		var selection = self.createSelection();
		
		var selector = new CommonSelector({
			ui : {
				windowId : self.context().ui.windowId,
				title : self.context().ui.title,
				icons : [ 
					{
						name : "전사관리자",
						url : "/resources/abc/images/ico_user5.png"
					},
					{
						name : "부서장",
						url : "/resources/abc/images/ico_user1.png"
					}
				]
			},
			node : {
				validator : function(node) {
					return self.canAddNode(node);
				}
			},
			apply : self.context().callback,
			panels : [organization, group, search],
			selection : selection
		});
		
		this.setSelector(selector);
	},
	
	createSelection : function() {
		var self = this;
		
		var type = self.context().selection.type;
		var selectionId = self.context().ui.windowId + "-selection-" + self.context().windowNo;
		
		if (type == "default") {
			return new OrganizationSelection(selectionId, function(node, data) {});
		}
		
		if (type == "privilege") {
			return new PrivilegeSelection(selectionId, function(node, data) {});
		}
		
		return null;
	},
	
	/**
	 * Open organization window. 
	 * @param params the parameter has a node array. Each node has key, type and text
	 * 		   params.nodes an array of nodes to add a selectio list initially
	 */
	open : function(params) {
		this.selector().open(params);
	},
	
	addNode : function(node) {
		var self = this;
		self.selector().addNode(node);
	},
	
	canAddNode : function(node) {
		var self = this;
		
		if (node.type == "virt") return false;
		if(!self.context().node.myself && node.type == "user" && node.key == gUsr_key) return false;
				
		return self.canSupportNodeType(node.type);
	},
	
	canSupportNodeType : function(type) {
		var self = this;
		
		var allowed = self.context().node.types; 
		if (!allowed || allowed.length == 0) return false;
		
		for (var i = 0; i < allowed.length; i++) {
			console.log("OrganizationWindow.canAdd(node) : allowed: " + allowed[i] + ", " + type);
			if (type == allowed[i]) return true;
		}
	},
	
	canSupportGroup : function() {
		var self = this;
		return self.canSupportNodeType("grp");
	},
	
	onClose : function() {
		var self = this;
		self.selector().onClose();
	}
	
};

/**
 * 사용자만 선택할 수 있는 조직도 윈도우. 조직도 윈도우와 동일한 파라미터를 가진다.
 * @param query 선택된 사용자가 들어갈 Element 쿼리
 * @param params 조직도 윈도우 파라미터
 */
var UserOrganizationWindow = function(query, params) {
	var window = null;
	var context = params;
	var query = query;
	
	this.window		= function() { return window; };
	this.context	= function() { return context; };
	this.query		= function() { return query; };
	
	this.setWindow	= function(val) { window = val; };	
};

UserOrganizationWindow.prototype = {
	load : function() {
		if (this.window() != null) return;
		
		var self = this;
		var context = self.context();
		context.callback = function(nodes) {
			self.onSelectOnTree(self.query(), nodes);
		}
		
		var window = new OrganizationWindow(context);
		self.setWindow(window);
	},
	
	open : function() {
		this.load();
		
		var nodes = new Array();
		$(this.query() + " li").each(function(i) {
			nodes.push({
				key:  $(this).attr("data-id"),
				type: "user",
				text: $(this).text()
			});
		});
		
		this.window().open({
			nodes : nodes
		});
	},
	
	onSelectOnTree : function(query, nodes) {
		var self = this;
		
		$(query).empty();
		for (var i = 0; i < nodes.length; i++) {
			$(query).append("<li data-id=\"" + nodes[i].key + "\">"+ nodes[i].text +"</li>");
		}
	}
};
