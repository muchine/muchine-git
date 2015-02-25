package com.lgu.abc.core.plugin.proto.workspace.counter;

public interface WorkspaceCounter {

	int countArticles(String workspaceId, String userId);
	
	int countComments(String workspaceId, String userId);
	
}
