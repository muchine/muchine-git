package com.lgu.abc.core.base.security.role.workspace;

import java.util.ArrayList;
import java.util.Collection;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.base.component.WorkspaceComponent;
import com.lgu.abc.core.prototype.workspace.base.component.WorkspaceComponentRegistry;

public abstract class WorkspaceRole extends AbstractRole {

	protected WorkspaceRole(String workspaceId) {
		super(workspaceId);
	}
	
	@Override
	protected Collection<Permissible> initialize(String rootId) {
		/*
		 * CAUTION Be very careful the order of permissions because permission sorting functionality has not yet been implemented.
		 * First permission will be checked first when resource patterns of permissions conflicts.
		 */
		Collection<Permissible> permissibles = new ArrayList<Permissible>();
		
		setWorkspacePrivilege(rootId, permissibles);
		setComponentPrivileges(rootId, permissibles);
		
		return permissibles;
	}
	
	private void setWorkspacePrivilege(String rootId, Collection<Permissible> permissibles) {
		ResourceID resource = new ResourceID(Workspace.class, rootId);
		Permission permission = new Permission(resource, defaultWorkspacePrivilege());
		
		permissibles.add(permission);
	}
	
	private void setComponentPrivileges(String rootId, Collection<Permissible> permissibles) {
		for (WorkspaceComponent component : WorkspaceComponentRegistry.all()) {
			ResourceID resource = new ResourceID(Workspace.class, rootId).concatenate(new ResourceID(component.resourceId()));
			Permission permission = new Permission(resource, defaultComponentPrivilege());
			
			permissibles.add(permission);
		}
	}
	
	protected abstract Privilege defaultWorkspacePrivilege();
	
	protected abstract Privilege defaultComponentPrivilege();

}
