package com.lgu.abc.core.prototype.ui.widget.position;

import lombok.Data;

import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;

public @Data class Disposition {

	private final WidgetPosition position;
	
	private final int order;
	
	public Disposition(WidgetPosition position, int order) {
		this.position = position;
		this.order = order;
	}
	
}
