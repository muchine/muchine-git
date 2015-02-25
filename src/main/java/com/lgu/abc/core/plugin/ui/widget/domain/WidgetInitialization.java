package com.lgu.abc.core.plugin.ui.widget.domain;

import lombok.ToString;

import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;

@ToString
public final class WidgetInitialization {
	
	private final boolean supported;
	
	private final WidgetInitializationType[] types;
	
	public WidgetInitialization(WidgetCategory category, int order) {
		this(true, new WidgetInitializationType[] {
			new WidgetInitializationType(category, order)
		});
	}
	
	public WidgetInitialization(WidgetInitializationType[] types) {
		this(true, types);
	}
	
	public WidgetInitialization(boolean supported, WidgetInitializationType[] types) {
		this.supported = supported;
		this.types = types;
	}
	
	public boolean isSupported() {
		return supported;
	}
	
	public WidgetInitializationType[] types() {
		return types;
	}
	
	public boolean hasCategory(WidgetCategory category) {
		for (WidgetInitializationType type : types) {
			if (category.equals(type.category())) return true;
		}
		
		return false;
	}
	
	public int order(WidgetCategory category) {
		for (WidgetInitializationType type : types) {
			if (category.equals(type.category())) return type.order();
		}
		
		throw new IllegalStateException("The order not found for category: " + category);
	}
	
}
