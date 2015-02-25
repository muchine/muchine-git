//==============================================================================
// Class			: usrOrgan()
//------------------------------------------------------------------------------
// 조직도 Class
// var usrOrgan = new UsrOrganCls('조직도 출력할 랩퍼 아이디',  '노드 선택시 호출할 함수')
var UsrOrganCls = function(treeBxId, treeView_onSelect, displayNode) {

	var _treeBxId	= null;									// 조직도를 출력할 랩퍼 아이디
	var _onSelect	= null;									// 노드 선택시 실행할 이벤트 핸들러
	var _displayNode= (displayNode) ? displayNode : 'all';
	
	var _dataSrc_url= gContextPath + "/usr/organ.json?model_query_pageable.enable=false&displayNode="+_displayNode;	// 조직도 데이터를 가져오는 주소
	var _dataSrc	= null;									// 조직도 JSON 데이터
	
	var _organTree	= null;									// 생성한 조직도 객체 (kendoTreeView 객체)
	var _curNode	= {type:null, name:null, key:null};		// 현재 선택되어 있는 노드 정보
	
	
	if(arguments.length > 0) {
		_treeBxId	= (arguments[0])? arguments[0] : null;
		_onSelect	= (arguments[1])? arguments[1] : null;
	}
	
	//=== getter & setter
	//--- getter
	this.get_treeBxId	= function() { return _treeBxId; };
	this.get_onSelect	= function() { return _onSelect; };
	this.get_dataSrc_url= function() { return _dataSrc_url; };
	this.get_dataSrc	= function() { return _dataSrc; };
	this.get_organTree	= function() { return _organTree; };
	this.get_curNode	= function() { return _curNode; };
	
	//--- setter
	this.set_treeBxId	= function(val) { _treeBxId = val; };
	this.set_onSelect	= function(val) { _onSelect = val; };
	this.set_dataSrc_url= function(val) { _dataSrc_url = val; };
	this.set_dataSrc	= function(val) { _dataSrc = val; };
	this.set_organTree	= function(val) { _organTree = val; };
	this.set_curNode	= function(val) { _curNode = val; };

	
	//-- 조직도 트리뷰 출력
	if(_treeBxId != null)
		this.append_treeView();
};

UsrOrganCls.prototype = {
	
	set : function() {
		
		if(arguments.length > 0) {
			this.set_treeBxId( (arguments[0])? arguments[0] : null );
			this.set_onSelect( (arguments[1])? arguments[1] : null );
		}
		
		// 조직도 트리뷰 출력
		if(this.get_treeBxId() != null)
			this.append_treeView();
	},
	
	append_treeView	: function() {
		
		this.load_dataSrc();
		
		var treeBxId = this.get_treeBxId();
		var dataSrc	 = this.get_dataSrc();
		
		if(this.get_organTree()) {
			$(treeBxId).data("kendoTreeView").setDataSource(dataSrc);
		}
		else {

			var oThis = this;
			
			$(treeBxId).kendoTreeView({
				dataSource	: dataSrc,
				dataTextField: "nodeName",
				//template	: "#= ■ item.dept_nm # ",
				select		: function(e) {
					var dataItem = this.dataItem(e.node);
					oThis.onSelect(dataItem);
				}
		    });
			
			var organTree = $(treeBxId).data("kendoTreeView");
			// 트리 펼치기
			var oFirstNode = $(treeBxId+" .k-first")[0];
			    organTree.expand(oFirstNode);
			    
			// 이미 선택된 노드 재선택 가능하도록 추가 이벤트 핸들링 연결
			$(treeBxId+" .k-state-selected").live("click", function(evt) {
				$(treeBxId+" .k-state-selected").removeClass("k-state-selected");
			});
			
			oThis.set_organTree(organTree);
		}
	},
	
	load_dataSrc	: function() {
		
		var oThis = this;
		$.ajax({
			url		: this.get_dataSrc_url(),
			async	: false,
			cache	: false,
			success : function(jData) {
				//console.log("UsrOrganCls.load_dataSrc.sucess() ================= \n"+JSON.stringify(jData));
				
	             var dataSrc = new kendo.data.HierarchicalDataSource({
	             	data: jData,
	                 schema: {
	                     model: {
	                         children: "chldList"
	                     }
	                 }
	             });
	
	             oThis.set_dataSrc(dataSrc);
			}
		});
	},
	
	//-- e핸들러	: 조직도 노드 선택시
	onSelect		: function(dataItem) {
	
		var curNode = this.get_curNode();
			curNode.type = dataItem.nodeType;
			curNode.name = dataItem.nodeName;
			curNode.key  = dataItem.nodeKey;
			
		this.set_curNode(curNode);
		
		// 사용자 e핸들러 호출
		if(this.get_onSelect()) {
			// this.get_onSelect()(dataItem);
			var callBack = this.get_onSelect();
			
			if(typeof callBack === "function") {
				callBack(dataItem);
			}
			else if(callBack.constructor == String) {
				eval(callBack)(dataItem);
			}
		}
	}
};
