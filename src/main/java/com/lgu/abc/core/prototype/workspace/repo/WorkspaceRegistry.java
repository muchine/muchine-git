package com.lgu.abc.core.prototype.workspace.repo;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.workspace.Workspace;

@Component
public class WorkspaceRegistry {

	protected static final Log logger = LogFactory.getLog(WorkspaceRegistry.class);
	
	private static WorkspaceRepository repository;
	
	@Autowired
	public WorkspaceRegistry(WorkspaceRepository repo) {
		Validate.isTrue(repository == null, "repository should be instantiated only once by Spring.");
		repository = repo;
	}
	
	public static Workspace get(String workspaceId) {
		if (workspaceId == null) return null;
		return repository == null ? null : repository.read(workspaceId);
	}
	
	public static void evict(String workspaceId) {
		logger.debug("evict workspace: " + workspaceId);
		repository.evict(workspaceId);
	}
	
}
