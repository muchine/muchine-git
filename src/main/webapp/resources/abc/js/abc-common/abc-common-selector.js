/**
 * 선택 윈도우
 * @param ui.windowId 선택 윈도우가 오픈되는 윈도우 개체 ID
 * @param ui.title 선택 윈도우 제목
 * @param ui.icons 윈도우 하단에 보여줄 아이콘 목록
 * 		   ui.icons[i].name 보여줄 아이콘 이름
 * 		   ui.icons[i].url 이미지 URL
 * @param node.validator 노드 선택 시 유효성 검사 함수
 * @param apply 적용 버튼 클릭 시 호출될 callback 함수
 * @param panels 각 탭을 구성할 객체 리스트. (append, paint, clear, focus 함수 구현 필요)
 * 		   panels[i].append 각 탭의 제목이 되는 ul 요소를 추가하는 함수
 * 		   panels[i].paint 실제 패널 내용을 페인팅하는 함수
 * 		   panels[i].clear 패널 내용을 클리어하는 함수
 * 		   panels[i].focus 패널 포커스 시 호출되는 함수
 * @param selection 선택된 노드들을 표현할 프레임 개체. selection 개체는 아래 메소드들을 구현해야 한다.
 * 		   selection.header 선택 프레임의 헤더를 그리는 함수
 * 		   selection.paint 선택 프레임의 내용을 그리는 함수
 * 		   selection.add 선택 프레임에 선택된 노드를 추가하는 함수
 * 		   selection.items 선택 프레임에서 현재 선택되어 있는 모든 노드를 조회하는 함수
 * 		   selection.clear 선택 프레임을 초기화하는 함수 
 */
var CommonSelector = function(params) {
	if(window.commonSelectorCounter == 0 || window.commonSelectorCounter > 0)
		window.commonSelectorCounter++;
	else
		window.commonSelectorCounter = 0;
	
	var windowNo		= window.commonSelectorCounter;
	
	var context			= params;
	var windowObject	= null;	
	var selection		= null;
	
	this.windowNo		= function() { return windowNo; };
	this.context		= function() { return context; };

	this.windowObject	= function() { return windowObject; };
	this.selected		= function() { return context.selection; };
	
	this.setWindowObject	= function(val) { windowObject = val; };

	// windowId 로 kendoWindow 가 계속 만들어지는 현상 때문에 이미 있으면 강제로 삭제함
	var windowId = params.ui.windowId;
	var alreadyKendoWindow = $("div[id=" + windowId + "]:eq(1)").data("kendoWindow"); // windowId 의 kendoWindow 변수
	if (typeof (alreadyKendoWindow) != "undefined") { // windowId 로 이미 kendoWindow 가 있으면
		alreadyKendoWindow.destroy(); // windowId 의 kendoWindow 삭제
	}

	this.init();
}

CommonSelector.prototype = {
	init : function() {
		var self = this;
		
		var windowId = "#" + this.context().ui.windowId;
		$(windowId).kendoWindow({
			width	: "700px",
			height	:"430px",
			title	: this.context().ui.title,
			modal	:true
	     });
		
		this.setWindowObject($(windowId));
		this.paint();
		
		var panels = this.context().panels;
		for (var i = 0; i < panels.length; i++) {
//			panels[i].bind(self.addNode);
		}
	},
	
	/*
	 * Painting methods
	 */
	paint : function() {
		var tabsId = "common-selector" + this.windowNo();
		var tabs = $("<div></div>").attr({"id": tabsId, "class":"win_organ_tabStrip"});
		
		var box = $("<div></div>").addClass("win_leftBx");
		box = $(box).append(tabs);
		
		var window = this.windowObject().addClass("win_organ");
		$(window)
			.append(box)
			.append(this.icons())
			.append(this.selected().header())
			.append(this.buttons())
			.append(this.footer());
				
		this.tabs(tabs);
		this.selected().paint();
	},
	
	tabs : function(tabs) {
		var tabUls = $("<ul></ul>");
		
		var panels = this.context().panels;
		for (var i = 0; i < panels.length; i++) {
			panels[i].append(tabUls);
		}
		
		tabs.append(tabUls);
		
		for (var i = 0; i < panels.length; i++) {
			panels[i].paint(tabs);
		}
						
		tabs.kendoTabStrip({
			animation:	{
				open: {duration: 0, effects: "fadeIn"}
			}
		});
		
		tabs.data("kendoTabStrip").select(0);
	},
	
	footer : function() {
		return $("<b></b>").addClass("F_12_black_n").text("");
	},
	
	icons : function() {
		var iconList = this.context().ui.icons;
		
		var icons = $("<div></div>").addClass("textbullet win_bottomBx");
		
		for (var i = 0; i < iconList.length; i++) {
			var image = $("<img />").addClass("k-image").attr("src", iconList[i].url);	// 부서장
			var em  = $("<em></em>").append(image).append(" " + iconList[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;");
			icons = $(icons).append(em);
		}
		
		return icons;
	},
		
	selectionId : function() {
		return this.context().ui.windowId + "-selection-" + this.windowNo();
	},
	
	buttons : function() {
		var self = this;
		
		var apply = $("<button></button>").addClass("k-button").text("적용");
		$(apply).on("click", function() {
			self.onApply();
		});
		
		var cancel = $("<button></button>").addClass("k-button").text("취소");
		$(cancel).on("click", function() {
			self.onClose();
		});
		
		var buttons = $("<div></div>").addClass("win_organ_btnBx");
		buttons = $(buttons).append(apply).append("&nbsp;&nbsp;").append(cancel);
			
		return buttons;
	},
	
	/*
	 * Action methods
	 */
	
	/**
	 * Open organization window. 
	 * @param params the parameter has a node array. Each node has key, type and text
	 * 		   params.nodes an array of nodes to add a selectio list initially
	 */
	open : function(params) {
		var self = this;
		// 조직도에서 이전 선택했던 노드 비활성화 시키지 (아무것도 선택 안 된 상태로 만들기)
		self.clear();
		
		// 오픈시 selectList 에 표시할 기본 데이터가 있다면, 추가 
		if(params && params.nodes && params.nodes.constructor == Array && params.nodes.length > 0) {			
			for(var i = 0; i < params.nodes.length; i++) {
				self.selected().add(params.nodes[i]);
			}
		}
		
		var window = self.windowObject();
		
		window.data("kendoWindow").center();
		window.data("kendoWindow").open();

		self.context().panels[0].focus();
	},

	// 조직도 + 검색결과 Tree 의 노드 클릭시 실행할 함수
	addNode : function(node) {
		var self = this;
		
		var validator = self.context().node.validator;
		if (validator(node)) self.selected().add(node);
	},
			
	clear : function() {
		this.selected().clear();
		
		var panels = this.context().panels;
		for (var i = 0; i < panels.length; i++) {
			panels[i].clear();
		}
	},
	
	onApply	: function() {
		var items  = this.selected().items();

		var apply = this.context().apply;
		var callbackCloseWindow = apply(items);
		if (typeof (callbackCloseWindow) != "undefined" && callbackCloseWindow) return;

		this.onClose();
	},
	
	onClose : function() {
		this.windowObject().data("kendoWindow").close();
	}
	
}

/**
 * 공통으로 사용 가능한 탭 패널
 * @param ui.treeId 트리가 표시될 요소 ID
 * @param ui.title 탭 패널 제목
 * @param transport.url 패널에 그릴 노드들을 가져오는 URL
 * @param transport.data 노드를 가져오는 URL과 함께 보낼 파라미터
 * @param tree.parameterize 트리 생성 시 파라미터에 대한 추가 작업을 담당하는 함수
 * @param callback	노드 클릭 시 호출될 함수
 */
var CommonPanel = function(params) {
	var context = params;
	var nodes	= null;
	
	this.context	= function() { return context; };
	this.nodes		= function() { return nodes; };
	
	this.setNodes	= function(val) { nodes = val; };
}

CommonPanel.prototype = {
	append : function(tabs, active) {
		var title = this.context().ui.title;
		
		var tab = $("<li></li>").text(title);
		if (active) tab.addClass("k-state-active");
		
		tabs.append(tab);
		return tabs;
	},
	
	paint : function(parent) {
		var treeId = this.context().ui.treeId;
		var tab = $("<div></div>").append($("<div></div>").attr({"id": treeId, "class":"win_organ_tree"}));
		parent.append(tab);
		
		this.load();
	},
	
	clear : function() {},
	
	reload : function() {
		var self = this;
		var treeview = $("#" + this.context().ui.treeId).data("kendoTreeView");
		
		self.data();
		treeview.setDataSource(self.nodes());
		treeview.expand(".k-item:first");
	},
		
	load : function() {
		this.data();
		
		var treeId	= "#" + this.context().ui.treeId;
		var self = this;
		
		var params = {
			dataSource : this.nodes(),
			dataTextField : "text",
			select : function(e) {
				var callback = self.context().callback;
				callback(this.dataItem(e.node));
			}
		};
		
		var parameterize = self.context().tree.parameterize;
		if (parameterize) parameterize(params);
		$(treeId).kendoTreeView(params);
		
		var tree = $(treeId).data("kendoTreeView");
		
		// 트리 펼치기
		var first = $(treeId + " .k-first")[0];
		tree.expand(first);
		
		// 이미 선택된 노드 재선택 가능하도록 추가 이벤트 핸들링 연결
		$(treeId + " .k-state-selected").live("click", function(evt) {
			$(treeId + " .k-state-selected").removeClass("k-state-selected");
		});
	},
	
	data : function() {
		var self = this;

		$.ajax({
			url		: self.context().transport.url,
			data	: self.context().transport.data,
			async	: false,
			cache	: false,
			success : function(response) {
				var nodes = new kendo.data.HierarchicalDataSource({
					data: response,
	             	schema: {
	                	 model: {
	                    	 children: "child"
	                     }
	                 }
				});
	
	            self.setNodes(nodes);
			}
		});
	}
	
}