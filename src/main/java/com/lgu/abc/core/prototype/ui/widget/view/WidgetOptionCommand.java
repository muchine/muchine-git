package com.lgu.abc.core.prototype.ui.widget.view;

import javax.validation.constraints.NotNull;

import lombok.Data;

import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetLocation;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;

public abstract @Data class WidgetOptionCommand<T extends WidgetOption> {

	@NotNull
	private String type;
	
	private String itemId = "0";
	
	private WidgetPosition position;
	
	private int order;
	
	public T create(WidgetCategory category, WidgetLocation location) {
		return create(category, location.getPosition(), location.getOrder());
	}
	
	public T create(WidgetCategory category, WidgetPosition position, int order) {
		T option = instantiate();
		
		option.setCategory(category);
		option.setType(type);
		option.setItemId(itemId);
		option.setPosition(position);
		option.setOrder(order);
		
		return option;
	}
	
	public T create(WidgetCategory category) {
		return create(category, position, order);
	}
	
	protected abstract T instantiate();
	
}
