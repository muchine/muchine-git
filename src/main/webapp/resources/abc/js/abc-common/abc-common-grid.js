/**
 * 공통으로 사용 가능한 그리드
 * @param gridId 그리드를 로딩할 요소 ID
 * @param source.url JSON 데이터를 로딩할 서버 URL
 * @param source.parameterize 서버 요청 시 전송할 파라미터를 가공하는 함수
 * @param grid.pageSize 그리드 페이지 크기
 * @param grid.columnSize 그리드 컬럼 수
 * @param grid.rowTemplateId 그리드 페인팅 시 사용할 Row Template 요소 ID
 * @param grid.dataBound 데이터 바인딩 시 호출되는 함수
 * @param grid.columns 그리드 컬럼 설정 배열
 */
var CommonGrid = function(param) {
	var context = param;
	
	var gridDataSource	= null;
	var gridObject		= null;
	var pageSize		= context.grid.pageSize;
	
	this.context		= function() { return context; };
	
	this.gridDataSource 	= function() { return gridDataSource; };
	this.gridObject		 	= function() { return gridObject; };
	this.pageSize			= function() { return pageSize; };
	
	this.setGridDataSource	= function(val) { gridDataSource = val; };
	this.setGridObject		= function(val) { gridObject = val; };
	this.setPageSize		= function(val) { pageSize = val; };
	
	this.load();
};

CommonGrid.prototype = {
	load : function() {
		var self = this;
		var source = new kendo.data.DataSource({
			transport: {
				read: {
					url : self.context().source.url,
					datatype : "json",
					type : "GET"
				},
				parameterMap: function(data, type) {
					var params = {
						"model_query_pageable.pageSize" : data.pageSize,
						"model_query_pageable.pageNumber" : (data.page - 1)
					};
					
					self.context().source.parameterize(params);					
					return params;
				},
			},
			schema: {
				data : function(response) { 
					return response.content;
				},
				total : function(response) { 
					return response.totalElements;
				}
			},
			pageSize : self.pageSize(),
			serverPaging : true,
			serverSorting : true,
			sort : {} 
		});
		
		self.setGridDataSource(source);
	},
	
	paint : function() {
		var self = this;
		var gridId = self.context().gridId;
		
		$("#" + gridId).kendoGrid({
			dataSource: self.gridDataSource(), 
			rowTemplate: kendo.template($("#" + self.context().grid.rowTemplateId).html()),
			selectable : true,
			sortable : true,
			resizable : true,
			scrollable : false,
			pageable : { 
				refresh : true,
				pageSizes : abcKendoGrid.getPageablePageSizes(),
				pageSize : self.pageSize(),
				messages : abcKendoGrid.getPageableMessages()
			},
			dataBound : function(e) {
				if (self.gridDataSource().total() == 0) {
					abcKendoGrid.setNodata(self.context().grid.columnSize, gridId);
				}
				
				self.context().grid.dataBound(e);
				self.setPageSize(this.pager.pageSize());
			},
			columns : self.context().grid.columns
		});
		
		this.setGridObject($("#" + gridId).data("kendoGrid"));
	},
	
	refresh : function() {
		this.gridObject().dataSource.read();
		this.gridObject().refresh();
	},
	
	reload : function() {
		this.gridObject().dataSource.page(0);
		this.gridObject().refresh();
	},
	
};

/**
 * 공통으로 사용 가능한 그리드
 * @param gridId 그리드가 로딩된 요소 ID
 * @param box.allId 전체 선택 체크박스의 요소 ID
 * @param box.className 로우 체크박스의 클래스 이름
 * @param callback 이벤트 발생 시 호출되는 callback 함수
 */
var CommonGridCheckBoxes = function(param) {
	
	var context = param;
	
	var boxesQuery 		= "#" + context.gridId + " ." + context.box.className;
	var allBoxQuery 	= "#" + context.box.allId;
	
	this.context		= function() { return context; };
	this.boxesQuery		= function() { return boxesQuery; };
	this.allBoxQuery	= function() { return allBoxQuery; };
	
	this.init();
};

CommonGridCheckBoxes.prototype = {
	init : function() {
		var self = this;
		
		self.bindAllCheckBox();
		self.bindRowCheckBoxes();	
	},
	
	clear : function() {
		var self = this;
		self.setAllRowsChecked(false);
	},
	
	bindAllCheckBox : function() {
		var self = this;
		
		$(self.allBoxQuery()).live("click", function(event) {
			var e = event || window.event;
			var eTarget = e.target || e.srcElement;
			
			self.setAllRowsChecked(eTarget.checked);		
			e.stopPropagation();
		});
	},
	
	bindRowCheckBoxes : function() {
		var self = this;
		
		$(self.boxesQuery()).live("click", function(event) {
			var e = event || window.event;
			var eTarget = e.target || e.srcElement;
			
			self.setAllCheckBox(eTarget.checked);

			self.context().callback();
			e.stopPropagation();
		});
	},
	
	setAllRowsChecked : function(checked) {
		var self = this;
		 $(self.boxesQuery()).each(function(i) {
			$(this).attr("checked", checked);
		});
		
		$(self.allBoxQuery()).attr("checked", checked);
		
		self.context().callback();
	},
	
	setAllCheckBox : function(checked) {
		var self = this;
		var value = checked && self.isAllRowsChecked();
		$(self.allBoxQuery()).attr("checked", value);
	},
	
	isAllRowsChecked : function() {
		var self = this;
		
		var numberOfBox = $(self.boxesQuery()).length;
		var numberOfBoxChecked = $(self.boxesQuery() + ":checked").length;
		
		return numberOfBox > 0 && numberOfBox == numberOfBoxChecked;
	},
	
	isNoRowsChecked : function() {
		return $(self.boxesQuery() + ":checked").length == 0;
	}	
	
};