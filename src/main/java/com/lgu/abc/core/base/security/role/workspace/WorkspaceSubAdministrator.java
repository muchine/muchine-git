package com.lgu.abc.core.base.security.role.workspace;

import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;

public class WorkspaceSubAdministrator extends WorkspaceRole {

	public WorkspaceSubAdministrator(String workspaceId) {
		super(workspaceId);
	}

	@Override
	protected Privilege defaultWorkspacePrivilege() {
		return new Privilege(AccessLevel.READ);
	}

	@Override
	protected Privilege defaultComponentPrivilege() {
		return new Privilege(AccessLevel.MANAGE);
	}

}
