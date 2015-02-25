package com.lgu.abc.core.prototype.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.WorkspaceQuery;
import com.lgu.abc.core.prototype.workspace.repo.WorkspaceRepository;

@Service
public class WorkspaceServiceImpl extends DomainServiceImpl<Workspace, WorkspaceQuery> implements WorkspaceService {

	@Autowired
	public WorkspaceServiceImpl(WorkspaceRepository repository) {
		super(repository);
	}

}
