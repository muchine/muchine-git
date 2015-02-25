package com.lgu.abc.core.prototype.workspace.support;

import com.lgu.abc.core.exception.http.UnauthorizedException;
import com.lgu.abc.core.prototype.org.user.User;

public class WorkspaceRoleFilter {

	public static void isManager(User actor, String workspaceId) {
		if (!WorkspaceRoleMiner.isAdministrator(actor, workspaceId)) throw new UnauthorizedException();
	}
	
	public static void isMember(User actor, String workspaceId) {
		if (WorkspaceRoleMiner.isAdministrator(actor, workspaceId) || WorkspaceRoleMiner.isMember(actor, workspaceId)) return;
		throw new UnauthorizedException();
	}
		
}
