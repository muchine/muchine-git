/**
 * 자동 완성
 * @param ui.textId 자동 완성을 적용할 텍스트 개체 ID
 * @param transport.url 자동 완성 노드들을 가져오는 URL
 * @param transport.data 해당 URL로 함께 요청될 파라미터
 * @param callback 노드 클릭 시 실행될 함수
 */
var CommonAutoCompleter = function(params) {
	var context			= params;
	
	this.context		= function() { return context; };
	
	this.init();
};

CommonAutoCompleter.prototype = {
	init : function() {
		var self = this;
		var textId = self.context().ui.textId;
		
		self.bindKeyEvents(textId);
		
		$("#" + textId).kendoAutoComplete({
			select : function(e){
				self.context().callback(e.sender.dataItem(e.item.index()));
			},
			dataTextField: "name",
			filter: "contains",
			minLength: 1,
			delay: 100,
			dataSource: self.dataSource() 
		});
	},
	
	dataSource : function() {
		var self = this;

		return new kendo.data.DataSource({
			transport : {
				read : {
					url : self.context().transport.url,
					dataType : "json"
				},
				parameterMap: function(data, type) { // 호출 파라미터 정보
					return self.context().transport.data;
				}
			},
			schema: {
				data : function(response) { // 받아온 데이터(리스트) 세팅
					return response;
				}
			}
		})
	},

	bindKeyEvents : function(textId) {
		$("#" + textId).keydown(function(e) {
			if (e.keyCode == 13) {
				var autocomplete = $("#" + textId).data("kendoAutoComplete");
				var length = $("li.k-item.k-state-focused", autocomplete.ul).length;

				if (length == 0) autocomplete.select(autocomplete.ul.children().eq(0));
			}
		});
	}
	
};

