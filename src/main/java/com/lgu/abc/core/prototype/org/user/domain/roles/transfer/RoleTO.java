package com.lgu.abc.core.prototype.org.user.domain.roles.transfer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lombok.Data;

import com.lgu.abc.core.prototype.org.user.domain.roles.Role;

public @Data class RoleTO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private boolean initialized = false;
	
	private CompanyRoleTO company;
	
	private List<WorkspaceRoleTO> workspaces;
	
	private List<DepartmentRoleTO> departments;
	
	public void initialize(Role role) {
		if (initialized) return;
		
		addCompanyRoles(role);
		addWorkspaceRoles(role);
		addDepartmentRoles(role);
		
		initialized = true;
		logger.trace("role initialization done.");
	}
	
	private void addCompanyRoles(Role role) {
		if (company == null) return;
		role.add(company.permissibles());
	}
	
	private void addWorkspaceRoles(Role role) {
		if (workspaces == null) return;
		
		for (WorkspaceRoleTO r : workspaces) {
			logger.trace("workspace role: " + r);
			role.add(r.permissibles());
		}
	}
	
	private void addDepartmentRoles(Role role) {
		logger.trace("add department roles... to: " + departments);
		if (departments == null) return;
		
		for (DepartmentRoleTO r : departments) {
			logger.trace("department role: " + r);
			role.add(r.permissibles());
		}
	}
	
}
