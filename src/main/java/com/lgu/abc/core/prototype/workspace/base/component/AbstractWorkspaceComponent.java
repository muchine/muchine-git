package com.lgu.abc.core.prototype.workspace.base.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.prototype.workspace.base.count.WorkspaceCount;
import com.lgu.abc.core.prototype.workspace.base.count.WorkspaceCountQuery;

public abstract class AbstractWorkspaceComponent implements WorkspaceComponent {

	protected AbstractWorkspaceComponent(WorkspaceComponentRegistry registry) {
		registry.add(this);
	}
	
	@Override
	public Iterable<WorkspaceCount> countItems(WorkspaceCountQuery query) {
		return newEmptyCounts(query);
	}
	
	@Override
	public Iterable<WorkspaceCount> countComments(WorkspaceCountQuery query) {
		return newEmptyCounts(query);
	}

	private Iterable<WorkspaceCount> newEmptyCounts(WorkspaceCountQuery query) {
		List<WorkspaceCount> counts = new ArrayList<WorkspaceCount>();
		
		if (CollectionUtils.isEmpty(query.getUserIds())) return counts;
		for (String userId : query.getUserIds()) {
			counts.add(new WorkspaceCount(userId, 0));
		}
		
		return counts;
	}

}
