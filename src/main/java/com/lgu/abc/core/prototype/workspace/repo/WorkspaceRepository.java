package com.lgu.abc.core.prototype.workspace.repo;

import com.lgu.abc.core.prototype.base.repo.PrototypeRepository;
import com.lgu.abc.core.prototype.workspace.Workspace;

public interface WorkspaceRepository extends PrototypeRepository<Workspace> {

	public static final String CACHE_NAME = "workspaces";
	
}
