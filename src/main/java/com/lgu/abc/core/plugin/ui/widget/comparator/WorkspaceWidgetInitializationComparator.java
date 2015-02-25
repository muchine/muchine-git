package com.lgu.abc.core.plugin.ui.widget.comparator;

import java.util.Comparator;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;

public class WorkspaceWidgetInitializationComparator implements Comparator<Widget> {

	private static final WorkspaceWidgetInitializationComparator instance = new WorkspaceWidgetInitializationComparator();
	
	private final WidgetCategory category = WidgetCategory.workspace;
	
	private WorkspaceWidgetInitializationComparator() {}
	
	@Override
	public int compare(Widget o1, Widget o2) {
		return o1.initializationWithWorkspace().order(category) - o2.initializationWithWorkspace().order(category);
	}
	
	public static WorkspaceWidgetInitializationComparator instance() {
		return instance;
	}

}
