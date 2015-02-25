package com.lgu.abc.core.prototype.workspace.base.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.base.plugin.SortableComparator;

@Component
public class WorkspaceComponentRegistry {

	private static final List<WorkspaceComponent> components = new ArrayList<WorkspaceComponent>();
	
	public synchronized void add(WorkspaceComponent component) {
		PluggableUtils.add(component, components);
		Collections.sort(components, SortableComparator.instance());
	}
	
	public static WorkspaceComponent find(String componentId) {
		for (WorkspaceComponent component : components) {
			if (component.id().equals(componentId)) return component;
		}
		
		return null;
	}
		
	public static List<WorkspaceComponent> all() {
		return components;
	}
	
}
