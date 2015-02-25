package com.lgu.abc.core.plugin.ui.widget.factory;

import java.util.List;

import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.workspace.Workspace;

public class WorkspaceWidgetFetcher implements WidgetFetchable {

	private final Workspace workspace;
	
	public WorkspaceWidgetFetcher(Workspace workspace) {
		this.workspace = workspace;
	}
	
	@Override
	public List<Widget> fetch(WidgetFactory factory) {
		return factory.create(workspace);
	}

}
