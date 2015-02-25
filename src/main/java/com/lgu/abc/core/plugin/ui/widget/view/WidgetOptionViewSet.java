package com.lgu.abc.core.plugin.ui.widget.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.plugin.ui.widget.WidgetID;
import com.lgu.abc.core.plugin.ui.widget.WidgetRegistry;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.prototype.workspace.Workspace;

public @Data class WidgetOptionViewSet {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final List<WidgetOptionView> content = new ArrayList<WidgetOptionView>();
	
	public <T extends WidgetOption> WidgetOptionViewSet(Iterable<T> options, User actor) {
		List<Widget> widgets = WidgetRegistry.enabled(actor);
		addWidgetOptionView(options, widgets, actor);
	}
	
	public <T extends WidgetOption> WidgetOptionViewSet(Iterable<T> options, User actor, Workspace workspace) {
		List<Widget> widgets = WidgetRegistry.enabled(workspace);
		addWidgetOptionView(options, widgets, actor);
	}
	
	private <T extends WidgetOption> void addWidgetOptionView(Iterable<T> options, List<Widget> widgets, User actor) {
		for (WidgetOption option : options) {
			Widget found = findWidget(option, widgets);
			if (found != null) content.add(new WidgetOptionView(option, actor, found));
		}
	}
	
	private Widget findWidget(WidgetOption option, List<Widget> widgets) {
		WidgetID widgetId = option.widgetId();
		for (Widget widget : widgets) {
			if (widget.id().equals(widgetId)) return widget;
		}
		
		return null;
	}
	
}
