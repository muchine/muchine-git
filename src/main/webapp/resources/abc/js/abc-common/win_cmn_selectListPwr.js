//================================================== 
// SelectListPwrCls Class   (2013.04.06 - root2)
// 공용(일반) 조직도 윈도우의 selectList 출력

var SelectListPwrCls = function(params) {
	
	var _winNo				= params.winNo;
	var _wrapId				= params.wrapId;	// win_organ_selectList_0, _1, ...
	var _wrapWidth			= (params.width  > 100) ? params.width  : 100;
	var _wrapHeight			= (params.height > 150) ? params.height : 150;
	var _allowDupli 		= (params.allowDupli == true) ? true : false;	// 노드선택 중복허용 여부
	var _allowNode			= (params.allowNode) ? params.allowNode : "dept_grp_user";  // 선택추가 허용노드 (all, corp_dept_grp_user, corp_dept_user, grp_user)
	var _pwrCnfg			= params.pwrCnfg;
	
	var _call_onChange		= (params.onChange) ? params.onChange : null;
	var _oSelectorUl		= null;
	var _oCounter			= null;
	
	var _defaultDataType= {
		text:'',
		nodeType:'', nodeKey:'', nodeName:'',
		corp_seq:0, corp_nm:'', dept_seq:0, dept_nm:'',
		usr_key:0, kor_nm:'',
		pos_nm:'', msng_id:'', msng_dial_nm:'',
		pwr_c:'N', pwr_r:'N', pwr_u:'N', pwr_d:'N'
	};
	
	//=== getter & setter
	//--- getter
	this.get_winNo				= function() { return _winNo; };
	this.get_wrapId 			= function() { return _wrapId; };
	this.get_wrapWidth  		= function() { return _wrapWidth; };
	this.get_wrapHeight 		= function() { return _wrapHeight; };
	this.get_allowDupli 		= function() { return _allowDupli; };
	this.get_allowNode			= function() { return _allowNode; };
	this.get_pwrCnfg			= function() { return _pwrCnfg; };
	this.get_pwrCnfgTextArr		= function() { return _pwrCnfg.text; };
	this.get_pwrCnfgDisplayArr	= function() { return _pwrCnfg.isDisplay; };
	this.get_pwrCnfgDisableArr	= function() { return _pwrCnfg.isDisable; };
	this.get_pwrCnfgCheckedArr	= function() { return _pwrCnfg.isChecked; };
	
	this.get_call_onChange		= function() { return _call_onChange; };
	this.get_oSelectorUl		= function() { return _oSelectorUl; };
	this.get_oCounter			= function() { return _oCounter; };
	this.get_defaultDataType	= function() { return _defaultDataType; };
	
	//--- setter
	this.set_oSelectorUl		= function(val) { return _oSelectorUl = val; };
	this.set_oCounter			= function(val) { return _oCounter = val; };
	
	if(params.wrapId) {
		this.init();
	}
};

SelectListPwrCls.prototype = {
		
	init	 : function() {
		console.log("SelectListPwrCls.init() ================= ");
		var oThis = this;
		
		// selectList 적용, 삽입
		this.appendHtml_wrapperChild();
		
		var oSelectorUl = $("ul", "#"+this.get_wrapId())[0];
		var win_rightBx = $("#"+this.get_wrapId()).parent();
		var oCounter 	= $(".counter", win_rightBx)[0];
		this.set_oSelectorUl( $(oSelectorUl) );
		this.set_oCounter( $(oCounter) );
		
		//--- e핸들러 설정
		// 노드 리스트 클릭
		$("li", "#"+this.get_wrapId()).live("click", oThis.onClick);
		//$("li", "#"+this.get_wrapId()).live("dblclick", function(evt) { oThis.onRemove(evt); });
		
		// 버튼 클릭
		/*
		$("button", "#"+this.get_wrapId()).each(function(index) {
			if(index == 0)
				$(this).live("click", function(evt) { oThis.onSort_upup(evt); });
			else if(index == 1)
				$(this).live("click", function(evt) { oThis.onSort_up(evt); });
			else if(index == 2)
				$(this).live("click", function(evt) { oThis.onSort_dn(evt); });
			else if(index == 3)
				$(this).live("click", function(evt) { oThis.onSort_dndn(evt); });
			else if(index == 4)
				$(this).live("click", function(evt) { oThis.onRemove(evt); });
		});
		*/
		$("button", "#"+this.get_wrapId()).each(function(index) {
			if(index == 0)
				$(this).live("click", function(evt) { oThis.onRemove(evt); });
		});
		
		// 권한) 체크박스 클릭처리	---------------------------------
		// 읽기
		$(".pwr_r", "#"+this.get_wrapId()).live("click", function() {
			var oLi = $(this).parent();
			if( !$(this).is(":checked") ) {
				if($(".pwr_c", oLi).is(":checked")) {
					$(".pwr_c", oLi).attr("checked", false);
				}		
				if($(".pwr_d", oLi).is(":checked")) {
					$(".pwr_d", oLi).attr("checked", false);
				}
			}
		});
		// 쓰기
		$(".pwr_c", "#"+this.get_wrapId()).live("click", function() {
			var oLi = $(this).parent();
			if( $(this).is(":checked") ) {
				$(".pwr_r", oLi).attr("checked", true);
			}
			else {
				if(! $(".pwr_r", oLi).is(":disabled")) {
					//$(".pwr_r", oLi).attr("checked", false);
				}
				if($(".pwr_d", oLi).is(":checked")) {
					$(".pwr_d", oLi).attr("checked", false);
				}
				//$(".pwr_d", oLi).attr("checked", false);
			}
		});
		// 관리
		$(".pwr_d", "#"+this.get_wrapId()).live("click", function() {
			var oLi = $(this).parent();
			if( $(this).is(":checked") ) {
				$(".pwr_c", oLi).attr("checked", true);
				$(".pwr_r", oLi).attr("checked", true);
			}
			else {
				if(! $(".pwr_r", oLi).is(":disabled")) {
					//$(".pwr_r", oLi).attr("checked", false);
				}
				//$(".pwr_c", oLi).attr("checked", false);
			}
		});
	},
	
	// selectList 내부의 html 요소 삽입
	appendHtml_wrapperChild : function() {
		console.log("SelectListPwrCls.appendHtml_wrapperChild() ================= ");
		
		var div_selectList = $("#"+this.get_wrapId());
		
		var selectList_ul  = $("<ul></ul>");
		var selectList_div = $("<div></div>");
		
		var selectList_div_btn = null;
		var selectList_div_btn_span= null;
		//var icoArr	= ['k-i-arrowhead-n', 'k-i-arrow-n', 'k-i-arrow-s', 'k-i-arrowhead-s', 'k-i-close'];
		var icoArr	= ['k-i-close'];
		
		for(var i=0; i < icoArr.length; i++) {
			selectList_div_btn = $("<button></button>").attr("class", "k-button");
			selectList_div_btn_span= $("<span></span>").attr("class", "k-icon "+icoArr[i]);
			
			$(selectList_div_btn).append(selectList_div_btn_span);
			$(selectList_div).append(selectList_div_btn);
		}

		$(div_selectList).append(selectList_ul).append(selectList_div);
		this.set_oSelectorUl(selectList_ul);
		
	},
	
	create_liNode : function(dataItem) {
		console.log("SelectListPwrCls.create_liNode(text) ================= : "+dataItem.kor_nm);
		
		var liText = "";
		
		if(dataItem.nodeType == "corp") {
			liText = dataItem.corp_nm;
		}
		else if(dataItem.nodeType == "dept") {
			liText = dataItem.dept_nm;
		}
		else if(dataItem.nodeType == "grp") {
			liText = dataItem.grp_nm;
		}
		else {
			liText = abcUsrShow.getUsrShow(dataItem.usr_key, '', dataItem.dept_nm, dataItem.pos_nm);
			//liText = abcUsrShow.getUsrShow(dataItem.usr_key);
			//liText 	= (dataItem.dept_nm) ? dataItem.dept_nm : dataItem.corp_nm;
			//liText += (dataItem.kor_nm)  ? " / "+dataItem.kor_nm+ " "+dataItem.pos_nm : '';
		}
		//console.log("liText : "+liText);	
		dataItem.text   = liText;
		var dataJsonStr = JSON.stringify(dataItem);
		var liNode		= $("<li></li>").attr({"data":dataJsonStr}).text(liText);
		
		var pwrCnfgTextArr 		= this.get_pwrCnfgTextArr();
		var pwrCnfgCheckedArr 	= this.get_pwrCnfgCheckedArr();
		var pwrCnfgDisableArr 	= this.get_pwrCnfgDisableArr();
		var pwrCnfgDisplayArr	= this.get_pwrCnfgDisplayArr();
		
		var dataItemPwrArr= [ dataItem.pwr_r, dataItem.pwr_c, dataItem.pwr_u, dataItem.pwr_d];
		var classNameArr  = ['pwr_r', 'pwr_c', 'pwr_u', 'pwr_d'];	//= text : ['읽기', '쓰기', '수정', '삭제']
		var inputName = inputNode = "";
		var isChecked = isDisabled = isDisplay = "";
		
		console.log("pwrCnfg values ==============================");
		for(var i=pwrCnfgTextArr.length-1, right=10; i >=0 ; i--) {
			console.log(i+" ===> "+classNameArr[i]+" : "+pwrCnfgCheckedArr[i]+" "+pwrCnfgDisableArr[i]+" "+pwrCnfgDisplayArr[i]);
			
			className = classNameArr[i];
			inputName = "winCK_"+this.get_winNo()+"_"+classNameArr[i];
			
			// 체크상태 설정값이 true 인 경우는, 선택한 노드의 값에 상관없이 기본적으로 체크된 상태로 표시 한다.
			isChecked = (pwrCnfgCheckedArr[i]) ? "checked" : ( (dataItemPwrArr[i]=="Y") ? "checked" : false );
			isDisabled= (pwrCnfgDisableArr[i]) ? "disabled" : false;
			isDisplay = (pwrCnfgDisplayArr[i]) ? "block" : "none";
			
			inputNode = $("<input />").attr({"type":"checkbox", "name":inputName, "class":className, "checked":isChecked, "disabled":isDisabled, "value":1})
									  .css({"display":isDisplay, "right":right+"px"});
			$(liNode).append(inputNode);
			
			if(pwrCnfgDisplayArr[i]) { right += 25; }
		}
		
		return liNode;
	},
	
	// 이미 선택, 등록된 조직도 노드가 있는지 판별
	isExist		: function(dataItem) {
		console.log("SelectListPwrCls.isExist(dataItem) ================= : "+dataItem.usr_key+" "+dataItem.dept_nm+" "+dataItem.kor_nm);

		var result  = false;
		var liNodeArr = $("li", "#"+this.get_wrapId());
		var liNode	= null;
		var tmpData	= "";
		
		for(var i=0; i < liNodeArr.length; i++) {
			liNode 	= liNodeArr[i];
			tmpData	= JSON.parse( $(liNode).attr("data") );
			
			if(dataItem.nodeType == tmpData.nodeType && dataItem.nodeKey == tmpData.nodeKey) {
				result = true;
				break;
			}
		}
		console.log("isExist ? "+result);
		return result;
	},
	
	// 현재 선택추가된 selectList 노드값 반환
	get_dataArr_selectList : function() {
		
		var dataArr = new Array();
		var oSelectorUl = this.get_oSelectorUl();
		var liNodeArr = $("li", oSelectorUl);
		var liNode	  = dataItem = null;
		
		for(var i=0; i<liNodeArr.length; i++) {
			liNode	= liNodeArr[i];
			dataItem= JSON.parse( $(liNode).attr("data") );
			
			$("input", liNode).each(function(index) {
				var className = $(this).attr("class");
				if(className == "pwr_r") { dataItem.pwr_r = (this.checked) ? "Y" : "N";}
				else if(className == "pwr_c") { dataItem.pwr_c = (this.checked) ? "Y" : "N";}
				else if(className == "pwr_u") { dataItem.pwr_u = (this.checked) ? "Y" : "N";}
				else if(className == "pwr_d") { dataItem.pwr_d = (this.checked) ? "Y" : "N";}
			});
			
			dataArr.push(dataItem);
		}
		
		return dataArr;
	},
	
	// 현재 선택(active)되어 있는 li 인덱스 반환
	get_active	: function() {
		return $(".k-state-selected", this.get_oSelectorUl()).index();
	},
	
	get_active_cnt : function(oSelectorUl) {
		return $(".k-state-selected", oSelectorUl).length;
	},
	
	//--- e핸들러
	// 조직도 노드를 selectList 의 li 요소 추가하는 함수
	onAdd_organNode : function(newDataItem) {
		console.log("SelectListPwrCls.onAdd_organNode(dataItem) ================= : "+newDataItem.nodeName);
		
		newDataItem.kor_nm = (newDataItem.kor_nm) ? newDataItem.kor_nm : '';
		newDataItem.pos_nm = (newDataItem.pos_nm) ? newDataItem.pos_nm : '';
		
		var dataItem = this.get_defaultDataType();
			if(! newDataItem) { return; }
			else { $.extend(dataItem, newDataItem); }
			
		var okAdd 	  = true;
		var allowNode = this.get_allowNode();
		console.log("allowNode : dataItem.nodeType === "+allowNode+" : "+dataItem.nodeType);
		
		// 중복 허용여부 확인
		if(okAdd == true && this.get_allowDupli() == false) {
			okAdd = ( this.isExist(dataItem) ) ? false : true;	// 이미 존재한다면 okAdd 는 false
		}
		
		if(okAdd == true && allowNode != "all" && allowNode != "corpdeptuser") {
			okAdd = (allowNode.indexOf(dataItem.nodeType) != -1) ? true : false;
		}
		console.log("okAdd ? "+okAdd);
		
		// 선택 노드 selectorUl에 삽입
		if(okAdd == true) {
			var liNode = this.create_liNode(dataItem);
			
			if(liNode) {
				this.get_oSelectorUl().append(liNode);
				var oCounter = this.get_oCounter();
				var cnt = $(oCounter).text();
				$(oCounter).text(++cnt);
			}
			else { okAdd = false; }
		}
		
		return okAdd;
	},
	
	// 노드선택 (selectorUl 의 li 요소 클릭)
	onClick : function(evt) {
		console.log("SelectListPwrCls.onClick(evt) ================= : "+evt);
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		
		if(eTarget.nodeName == "INPUT")
			eTarget = eTarget.parentNode;
			
		//$("li", $(eTarget).parent()).removeClass("k-state-selected");
		//$(eTarget).addClass("k-state-selected");
		/*
		$(eTarget).toggle(
			function() {
				$(this).addClass("k-state-selected");
			},
			function() {
				$(this).removeClass("k-state-selected");
			}
		);
		*/
		if( $(eTarget).attr("class") == "k-state-selected" )
			$(this).removeClass("k-state-selected");
		else
			$(this).addClass("k-state-selected");
		
		var dataItem = JSON.parse( $(eTarget).attr("data") );
		console.log("dataItem.text : "+dataItem.text);
	},
	
	// 정렬 - 최상으로
	onSort_upup : function(evt) {
		console.log("SelectListPwrCls.onSort_upup(evt) ================= : "+evt);
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		
		// 크롬 : 타겟 오류 예외처리
		if(eTarget.nodeName == "SPAN")
			eTarget = eTarget.parentNode;
		
		var wrapper 	= $(eTarget).parent().parent();
		var oSelectorUl = $("ul", wrapper)[0];
		
		if(this.get_active_cnt(oSelectorUl) > 1) {
			layCmn.alert({
				msg : '하나만 선택하십시오.',
				callback : function() {return;}
			});
			return false;
		}
		
		var curIndex 	= $(".k-state-selected", oSelectorUl).index();
		//var liLength	= $("li", oSelectorUl).length;
			//console.log("index : "+curIndex);
			//console.log("li length : "+liLength);
		
		if(curIndex > 0) {
			var curNode = $("li", oSelectorUl)[curIndex];
			$(oSelectorUl).prepend(curNode);
		}
	},
	
	// 정렬 - 위로
	onSort_up : function(evt) {
		console.log("SelectListPwrCls.onSort_up(evt) ================= : "+evt);
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		
		if(eTarget.nodeName == "SPAN")
			eTarget = eTarget.parentNode;
		
		var wrapper 	= $(eTarget).parent().parent();
		var oSelectorUl = $("ul", wrapper)[0];
		
		if(this.get_active_cnt(oSelectorUl) > 1) {
			layCmn.alert({
				msg : '하나만 선택하십시오.',
				callback : function() {return;}
			});
			return false;
		}
		
		var curIndex 	= $(".k-state-selected", oSelectorUl).index();
		//var liLength	= $("li", oSelectorUl).length;
			//console.log("index : "+curIndex);
			//console.log("li length : "+liLength);
		
		if(curIndex > 0) {
			var befoNode= $("li", oSelectorUl)[curIndex-1];
			var curNode = $("li", oSelectorUl)[curIndex];
			$(befoNode).before(curNode);
		}
	},
	
	// 정렬 - 아래로
	onSort_dn : function(evt) {
		console.log("SelectListPwrCls.onSort_dn(evt) ================= : "+evt);
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		
		if(eTarget.nodeName == "SPAN")
			eTarget = eTarget.parentNode;
		
		var wrapper 	= $(eTarget).parent().parent();
		var oSelectorUl = $("ul", wrapper)[0];
		
		if(this.get_active_cnt(oSelectorUl) > 1) {
			layCmn.alert({
				msg : '하나만 선택하십시오.',
				callback : function() {return;}
			});
			return false;
		}
		
		var curIndex 	= $(".k-state-selected", oSelectorUl).index();
		//var liLength	= $("li", oSelectorUl).length;
		//console.log("index : "+curIndex);
		//console.log("li length : "+liLength);
		
		if(curIndex != -1 && curIndex < liLength-1) {
			var afterNode= $("li", oSelectorUl)[curIndex+1];
			var curNode  = $("li", oSelectorUl)[curIndex];
			$(afterNode).after(curNode);
		}
	},
	
	// 정렬 - 최하로
	onSort_dndn : function(evt) {
		console.log("SelectListPwrCls.onSort_dndn(evt) ================= : "+evt);
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		
		if(eTarget.nodeName == "SPAN")
			eTarget = eTarget.parentNode;
		
		var wrapper 	= $(eTarget).parent().parent();
		var oSelectorUl = $("ul", wrapper)[0];
		
		if(this.get_active_cnt(oSelectorUl) > 1) {
			layCmn.alert({
				msg : '하나만 선택하십시오.',
				callback : function() {return;}
			});
			return false;
		}
		
		var curIndex 	= $(".k-state-selected", oSelectorUl).index();
		//var liLength	= $("li", oSelectorUl).length;
		//console.log("index : "+curIndex);
		//console.log("li length : "+liLength);
		
		if(curIndex != -1 && curIndex < liLength-1) {
			var curNode = $("li", oSelectorUl)[curIndex];
			$(oSelectorUl).append(curNode);
		}
	},
	
	// 노드삭제 (selectorUl 의 li 요소 제거)
	onRemove	: function(evt) {
		console.log("SelectListPwrCls.onRemove(evt) ================= : "+evt);
		var e = evt || window.event;
		var eTarget = e.target || e.srcElement;
		
		if(eTarget.nodeName == "SPAN")
			eTarget = eTarget.parentNode;
		
		var wrapper 	= $(eTarget).parent().parent();
		var oSelectorUl = $("ul", wrapper)[0];

		var active_cnt = this.get_active_cnt(oSelectorUl);

		$(".k-state-selected", oSelectorUl).remove();
		var win_rightBx = $(oSelectorUl).parent().parent();
		var oCounter 	= $(".counter", win_rightBx);
		var cnt = $(oCounter).text();
		if(cnt > 0)
			$(oCounter).text(cnt - active_cnt);
	}
};


/**
 * 조직도 권한 설정 리스트
 * @param wrapper 선택 리스트가 그려질 요소 ID
 * @param nodeMapper 노드 매핑 시 추가적인 매핑 기능을 구현한 함수. 노드와 매핑된 데이터를 파라미터로 받는다. 
 * 		   mapper(node, data) 선택된 원본 노드 및 셀렉터 리스트로 매핑될 데이터
 */
var PrivilegeSelection = function(wrapper, nodeMapper) {
		
	var wrapperId = wrapper;
	var mapper = function(node, data) {};
	if (nodeMapper) mapper = nodeMapper; 
	
	var list	= null;
	var counter	= null;
	
	this.wrapperId 	= function() { return wrapperId; };
	this.mapper		= function() { return mapper; };
	this.getList	= function() { return list; };
	this.getCounter	= function() { return counter; };
	
	this.setList	= function(val) { return list = val; };
	this.setCounter	= function(val) { return counter = val; };
	
	this.init();
};

PrivilegeSelection.prototype = {
		
	init : function() {
		var self = this;
		
		$(".pwr_r", "#" + self.wrapperId()).live("click", function() {
			self.setCheckBoxes($(this).parent(), $(this).prop("checked"), false, false);
		});
		
		$(".pwr_c", "#" + self.wrapperId()).live("click", function() {
			self.setCheckBoxes($(this).parent(), true, $(this).prop("checked"), false);
		});
		
		$(".pwr_d", "#" + self.wrapperId()).live("click", function() {
			self.setCheckBoxes($(this).parent(), true, true, $(this).prop("checked"));
		});
	},
	
	setCheckBoxes : function(row, readable, writable, manageable) {
		$(".pwr_r", row).prop("checked", readable);
		$(".pwr_c", row).prop("checked", writable);
		$(".pwr_d", row).prop("checked", manageable);
	},
	
	/**
	 * @Override Selection interface method
	 */
	header : function() {
		var selection = $("<div></div>").addClass("win_rightBx_pwr");
		var header = $("<header></header>");
		var count = $("<h4></h4>").html("총 <span class='counter'>0</span> 명 선택");
		var types = $("<p></p>").html("읽기쓰기관리");
		var box = $("<div></div>").attr({"id": this.wrapperId(), "class":"win_organ_selectList"});
		
		header = $(header).append(count).append(types);
		selection = $(selection).append(header).append(box);
		
		return selection;
	},
	
	/**
	 * @Override Selection interface method
	 */
	paint : function() {
		var wrapperId = "#" + this.wrapperId();
		
		var container = $(wrapperId);
		var ul  = $("<ul></ul>");
		var div = $("<div></div>");

		var button = $("<button></button>").attr("class", "k-button");		
		$(button).append($("<span></span>").attr("class", "k-icon k-i-close"));
		$(div).append(button);

		$(container).append(ul).append(div);
		this.setList(ul);
		
		var box = $(wrapperId).parent();
		var counter = $(".counter", box)[0];
		this.setCounter($(counter));
		
		var oThis = this;
		
		$("li", wrapperId).live("click", oThis.onClick);
		$("button", wrapperId).on("click", function(event) {
			oThis.onRemove(event);
		});
	},
	
	/**
	 * @Override Selection interface method
	 */
	add : function(node) {
		if (this.has(node)) {
			console.log("cannot add node... key: " + node.key);
			return false;
		}
		
		// a selection list keeps only key, type and text of node.
		var data = {key: node.key, type: node.type, text: node.text};
		var mapper = this.mapper();
		mapper(node, data);
		
		var stringified = JSON.stringify(data);
		console.log("create node on selection list: " + stringified);
		
		var item = $("<li></li>").attr({"data": stringified}).text(data.text);
		$(item).append("<input type='checkbox' class='pwr_r' style='display: block; right: 60px;'>");
		$(item).append("<input type='checkbox' class='pwr_c' style='display: block; right: 35px;'>");
		$(item).append("<input type='checkbox' class='pwr_d' style='display: block; right: 10px;'>");
		
		this.addPrivilege(node, item);
		
		this.getList().append(item);
		this.refreshCounter();
		
		return true;
	},
	
	addPrivilege : function(node, item) {
		if (!node.privilege) return;
		
		var self = this;
		var privilege = node.privilege;

		self.setCheckBoxes(item, privilege.readable, privilege.writable, privilege.manageable);
	},
	
	/**
	 * @Override Selection interface method
	 * 현재 선택추가된 selectList 노드값 반환
	 */
	items : function() {
		var items = new Array();
		
		var nodes = $("li", this.getList());
		for(var i = 0; i < nodes.length; i++) {
			var data = JSON.parse($(nodes[i]).attr("data"));
			console.log("get data item: " + JSON.stringify(data));
			
			data["privilege"] = {
				readable : $(".pwr_r", nodes[i]).prop("checked"),
				writable : $(".pwr_c", nodes[i]).prop("checked"),
				manageable : $(".pwr_d", nodes[i]).prop("checked")
			}
			
			items.push(data);
		}
		
		return items;
	},
	
	/**
	 * @Override Selection interface method
	 */
	clear : function() {
		this.getList().empty();
		this.refreshCounter();
	},
	
	// 이미 선택, 등록된 조직도 노드가 있는지 판별
	has : function(node) {
		var items = this.items();
		
		for (var i = 0; i < items.length; i++) {
			if(node.type == items[i].type && node.key == items[i].key) return true;
		}
		
		return false;
	},
		
	size : function() {
		return this.items().length;
	},
		
	refreshCounter : function() {
		$(this.getCounter()).text(this.size());
	},
	
	/*
	 * Event Handlers
	 */
	onClick : function(event) {
		var e = event || window.event;
		var target = e.target || e.srcElement;
		
		if(target.name == "INPUT") target = target.parentNode;
		
		/*
		 * node-selected 클래스를 별도로 지정하는 이유는 k-state-selected로만 삭제 시 선택된 패널 등 다른 요소들도 삭제가 되는 경우가 발생하였기 때문이다.
		 * added by Sejoon at 2014.01.16
		 */
		if( $(target).hasClass("k-state-selected")) {
			console.log("remove selected class...");
			$(target).removeClass("k-state-selected");
			$(target).removeClass("node-selected");
		}
		else {
			console.log("add selected class...");
			$(target).addClass("k-state-selected");
			$(target).addClass("node-selected");
		}
	},
		
	onRemove	: function(event) {
		$(".node-selected", this.getList()).remove();		
		this.refreshCounter();
	}
};