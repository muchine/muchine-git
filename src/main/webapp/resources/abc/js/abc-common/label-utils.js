var LabelUtils =  {

	createNewLabelNameTextNode : function(labelId, currentName, callback) {
		var textNode = $("<input />").attr({"class": "k-textbox", "value": currentName, "data-id": labelId});
		
		$(textNode).on("click", function(event) {
			LabelUtils.stopEvent(event);
		});
		
		$(textNode).on("keydown", function(event) {
			var e = event || window.event;
			
			if(e.which == 13) {
				$(this).trigger('blur');
				LabelUtils.stopEvent(event);
			}
		});
		
		$(textNode).on("blur", function(event) {
			var newName = this.value.trim();
			if (LabelUtils.validateNewLabelName(newName, currentName)) {
				callback(labelId, newName);
			} else {
				$("#newLabel").remove();
			}
			
			LabelUtils.stopEvent(event);
		});
		
		return textNode;
	},
	
	validateNewLabelName : function(newName, currentName) {
		return newName && $.trim(newName).length > 0
	},
	
	stopEvent : function(event) {
		var e = event || window.event;
		$ogs.stopBubble(e);
		$ogs.preventDefault(e);
	}
		
};

