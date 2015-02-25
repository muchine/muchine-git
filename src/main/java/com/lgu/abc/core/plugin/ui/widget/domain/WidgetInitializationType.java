package com.lgu.abc.core.plugin.ui.widget.domain;

import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;

public class WidgetInitializationType {

	private WidgetCategory category;
	
	private int order;
	
	public WidgetInitializationType(WidgetCategory category, int order) {
		this.category = category;
		this.order = order;
	}
	
	public WidgetCategory category() {
		return category;
	}
	
	public int order() {
		return order;
	}
		
}
