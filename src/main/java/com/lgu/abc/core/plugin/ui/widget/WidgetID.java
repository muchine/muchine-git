package com.lgu.abc.core.plugin.ui.widget;

import lombok.Data;

public @Data class WidgetID {

	private String type;
	
	private String itemId;
	
	public WidgetID() {}
	
	public WidgetID(String type, String itemId) {
		this.type = type;
		this.itemId = itemId;
	}
	
}
