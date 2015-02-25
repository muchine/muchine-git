package com.lgu.abc.core.prototype.ui.widget.domain;

import lombok.Data;

public @Data class WidgetLocation {

	private WidgetPosition position;
	
	private int order;
	
	public WidgetLocation() {}
	
	public WidgetLocation(WidgetPosition position, int order) {
		this.position = position;
		this.order = order;
	}
	
}
