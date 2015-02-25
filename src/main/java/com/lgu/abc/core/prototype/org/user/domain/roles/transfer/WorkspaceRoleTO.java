package com.lgu.abc.core.prototype.org.user.domain.roles.transfer;

import lombok.Data;

import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceAdministrator;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceLeader;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceMembership;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceSubAdministrator;
import com.lgu.abc.core.prototype.workspace.domain.WorkspaceMemberRole;

public @Data class WorkspaceRoleTO {

	private String workspaceId;
	
	private WorkspaceMemberRole role;
	
	public Permissible permissibles() {
		if (WorkspaceMemberRole.NORMAL.equals(role.getCode()))
			return new WorkspaceMembership(workspaceId);
		else if (WorkspaceMemberRole.ADMIN.equals(role.getCode()))
			return new WorkspaceAdministrator(workspaceId);
		else if (WorkspaceMemberRole.SUB_ADMIN.equals(role.getCode()))
			return new WorkspaceSubAdministrator(workspaceId);
		else if (WorkspaceMemberRole.LEADER.equals(role.getCode()))
			return new WorkspaceLeader(workspaceId);
		else
			throw new IllegalStateException("Invalid member role code: " + role.getCode());
	}
	
}
