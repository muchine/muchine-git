package com.lgu.abc.core.plugin.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.plugin.ui.widget.factory.UserWidgetFetcher;
import com.lgu.abc.core.plugin.ui.widget.factory.WidgetFactory;
import com.lgu.abc.core.plugin.ui.widget.factory.WidgetFetchable;
import com.lgu.abc.core.plugin.ui.widget.factory.WorkspaceWidgetFetcher;
import com.lgu.abc.core.plugin.ui.widget.selector.AnyWidgetSelectable;
import com.lgu.abc.core.plugin.ui.widget.selector.WidgetSelectable;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;

@Component
public class WidgetRegistry {
	
	private static final List<WidgetFactory> factories = new ArrayList<WidgetFactory>();
	
	public synchronized void add(WidgetFactory factory) {
		factories.add(factory);
	}
	
	public static Widget find(WidgetID id, User user) {
		return findByWidgetId(id, enabled(user));
	}
	
	public static Widget find(WidgetID id, Workspace workspace) {
		return findByWidgetId(id, enabled(workspace));
	}
	
	private static Widget findByWidgetId(WidgetID id, List<Widget> widgets) {
		for (Widget widget : widgets) {
			if (widget.id().equals(id)) return widget;
		}
		
		return null;
	}
	
	public static List<Widget> enabled(User user) {
		return enabled(user, AnyWidgetSelectable.instance());
	}
	
	public static List<Widget> enabled(User user, WidgetSelectable selector) {
		return fetch(user.getCompany(), new UserWidgetFetcher(user), selector);
	}
	
	public static List<Widget> enabled(Workspace workspace) {
		return enabled(workspace, AnyWidgetSelectable.instance());
	}
	
	public static List<Widget> enabled(Workspace workspace, WidgetSelectable selector) {
		return fetch(workspace.getCompany(), new WorkspaceWidgetFetcher(workspace), selector);
	}
	
	private static List<Widget> fetch(Company company, WidgetFetchable fetchable, WidgetSelectable selector) {
		List<Widget> widgets = new ArrayList<Widget>();
		for (WidgetFactory factory : factories) {
			Module module = factory.module(company);
			if (!module.isOpened() || !module.isEnabled()) continue;
			
			for (Widget widget : fetchable.fetch(factory)) {
				if (selector.canSelect(widget)) widgets.add(widget);
			}
		}
		
		return widgets;
	}
	
}
