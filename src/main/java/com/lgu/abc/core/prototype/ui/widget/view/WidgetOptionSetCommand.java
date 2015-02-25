package com.lgu.abc.core.prototype.ui.widget.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.domain.Listable;
import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;
import com.lgu.abc.core.prototype.ui.widget.position.WidgetDisposer;

public abstract @Data class WidgetOptionSetCommand<T extends WidgetOption, C extends WidgetOptionCommand<T>> 
		implements Listable<C> {
	
	private WidgetCategory category;
	
	protected WidgetOptionSetCommand() {}
	
	protected WidgetOptionSetCommand(WidgetCategory category) {
		this.category = category;
	}
	
	public List<T> reset() {
		return reset(category.disposer());
	}
	
	public final List<T> reset(WidgetDisposer disposer) {
		List<T> managers = new ArrayList<T>();
		if (getEntities() == null) return managers;
		
		final int size = getEntities().size();
		int index = 0;
		for (C command : getEntities()) {
			managers.add(command.create(category, disposer.location(index, size)));
			index++;
		}
		
		return managers;
	}
	
	public List<T> sort() {
		List<T> managers = new ArrayList<T>();
		if (getEntities() == null) return managers;
		
		for (C command : getEntities()) {
			managers.add(command.create(category));
		}
		
		return managers;
	}
	
}
