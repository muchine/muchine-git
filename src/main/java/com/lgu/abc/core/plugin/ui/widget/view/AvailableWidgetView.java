package com.lgu.abc.core.plugin.ui.widget.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.plugin.ui.widget.WidgetRegistry;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetType;
import com.lgu.abc.core.prototype.workspace.Workspace;

public @Data class AvailableWidgetView {

	private final List<Widget> content = new ArrayList<Widget>();
	
	public <T extends WidgetOption> AvailableWidgetView(Iterable<T> options, User actor, WidgetType type) {		
		List<Widget> enabled = WidgetRegistry.enabled(actor);
		for (Widget widget : enabled) {
			if (!contains(widget, options) && isSupportedType(type, widget)) content.add(widget);
		}
	}
	
	public <T extends WidgetOption> AvailableWidgetView(Iterable<T> options, Workspace workspace) {		
		List<Widget> enabled = WidgetRegistry.enabled(workspace);
		for (Widget widget : enabled) {
			if (!contains(widget, options)) content.add(widget);
		}
	}
	
	private <T extends WidgetOption> boolean contains(Widget enabled, Iterable<T> options) {
		if (IterableUtils.isEmpty(options)) return false;
		
		for (T option : options) {
			if (enabled.id().equals(option.widgetId())) return true;
		}
		
		return false;
	}
	
	private boolean isSupportedType(WidgetType type, Widget widget) {
		for (WidgetType t : widget.types()) {
			if (t.equals(type)) return true;
		}
		
		return false;
	}
	
}
