package com.lgu.abc.core.prototype.workspace.support;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;
import com.lgu.abc.core.base.security.role.workspace.WorkspaceRole;
import com.lgu.abc.core.base.security.visitor.PermissibleVisitor;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class WorkspaceCollector implements PermissibleVisitor {

	private final List<String> workspaceIds = new ArrayList<String>();
	
	@Override
	public void visit(AbstractRole role) {
		if (role instanceof WorkspaceRole) workspaceIds.add(role.resourceId());
		for (Permissible permissible : role) permissible.accept(this);
	}

	@Override
	public void visit(Permission permission) {

	}
	
	public static List<String> findActiveWorkspaces(User user) {
		WorkspaceCollector collector = new WorkspaceCollector();
		user.role().accept(collector);
		
		return collector.getWorkspaceIds();
	}

	public static List<String> findManagingWorkspaces(User user) {
		List<String> managed = new ArrayList<String>();
		
		List<String> workspaceIds = findActiveWorkspaces(user);
		if (CollectionUtils.isEmpty(workspaceIds)) return managed;
		
		for (String workspaceId : workspaceIds) {
			if (!WorkspaceRoleMiner.isAdministrator(user, workspaceId)) continue;
			managed.add(workspaceId);
		}
		
		return managed;
		
	}
	
}
