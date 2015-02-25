package com.lgu.abc.core.prototype.workspace.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.service.WorkspaceService;

@Component
public class WorkspaceFinder {

	@Autowired
	private WorkspaceService service;
	
	public Workspace findByWorkspaceId(String workspaceId) {
		Workspace workspace = new Workspace();
		workspace.setId(workspaceId);
		
		return service.read(workspace);
	}
	
}
