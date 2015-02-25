package com.lgu.abc.core.prototype.workspace.support;

import com.lgu.abc.core.base.security.role.workspace.WorkspaceAdministrator;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceLeader;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceMembership;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceSubAdministrator;
import com.lgu.abc.core.prototype.org.user.User;

public class WorkspaceRoleMiner {

	public static boolean isManager(User user, String workspaceId) {
		return user.hasRole(new WorkspaceAdministrator(workspaceId));
	}
		
	public static boolean isLeader(User user, String workspaceId) {
		return user.hasRole(new WorkspaceLeader(workspaceId));
	}
	
	public static boolean isSubManager(User user, String workspaceId) {
		return user.hasRole(new WorkspaceSubAdministrator(workspaceId));
	}
	
	public static boolean isMember(User user, String workspaceId) {
		return user.hasRole(new WorkspaceMembership(workspaceId));
	}
	
	public static boolean isAdministrator(User user, String workspaceId) {
		return WorkspaceRoleMiner.isManager(user, workspaceId) ||
				WorkspaceRoleMiner.isLeader(user, workspaceId) ||
				WorkspaceRoleMiner.isSubManager(user, workspaceId);
	}
	
	public static boolean isJoined(User user, String workspaceId) {
		return isAdministrator(user, workspaceId) || isMember(user, workspaceId);
	}
	
}
