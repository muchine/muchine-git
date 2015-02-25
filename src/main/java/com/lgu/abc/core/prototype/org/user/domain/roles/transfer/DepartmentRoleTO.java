package com.lgu.abc.core.prototype.org.user.domain.roles.transfer;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.role.department.DepartmentManager;

public @Data class DepartmentRoleTO {

	private String departmentId;
	
	private boolean manager;
		
	public DepartmentRoleTO() {}
	
	public DepartmentRoleTO(String departmentId, boolean manager) {
		this.departmentId = departmentId;
		this.manager = manager;
	}
	
	public List<Permissible> permissibles() {
		List<Permissible> permissibles = new ArrayList<Permissible>();
		if (manager) permissibles.add(new DepartmentManager(departmentId));
		
		return permissibles;
	}
	
}
