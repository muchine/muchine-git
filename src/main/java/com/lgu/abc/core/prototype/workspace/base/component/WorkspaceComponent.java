package com.lgu.abc.core.prototype.workspace.base.component;

import com.lgu.abc.core.base.plugin.Pluggable;
import com.lgu.abc.core.prototype.workspace.base.count.WorkspaceCount;
import com.lgu.abc.core.prototype.workspace.base.count.WorkspaceCountQuery;

public interface WorkspaceComponent extends Pluggable {

	String resourceId();
	
	String optionUri(String workspaceId);
		
	Iterable<WorkspaceCount> countItems(WorkspaceCountQuery query);
	
	Iterable<WorkspaceCount> countComments(WorkspaceCountQuery query);
		
}
