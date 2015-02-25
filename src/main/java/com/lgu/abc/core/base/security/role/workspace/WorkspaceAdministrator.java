package com.lgu.abc.core.base.security.role.workspace;

import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;

public class WorkspaceAdministrator extends WorkspaceRole {

	public WorkspaceAdministrator(String workspaceId) {
		super(workspaceId);
	}

	@Override
	protected Privilege defaultWorkspacePrivilege() {
		return new Privilege(AccessLevel.MANAGE);
	}

	@Override
	protected Privilege defaultComponentPrivilege() {
		return new Privilege(AccessLevel.MANAGE);
	}

}
