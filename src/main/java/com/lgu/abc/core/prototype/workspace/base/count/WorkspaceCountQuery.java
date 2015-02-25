package com.lgu.abc.core.prototype.workspace.base.count;

import java.util.List;

import lombok.Data;

public @Data class WorkspaceCountQuery {

	private final String workspaceId;
	
	private final List<String> userIds;
	
	public WorkspaceCountQuery(String workspaceId, List<String> userIds) {
		this.workspaceId = workspaceId;
		this.userIds = userIds;
	}
	
}
