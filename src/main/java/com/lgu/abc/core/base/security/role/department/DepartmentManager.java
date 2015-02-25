package com.lgu.abc.core.base.security.role.department;

import java.util.ArrayList;
import java.util.Collection;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.department.Department;

public class DepartmentManager extends AbstractRole {

	public DepartmentManager(String departmentId) {
		super(departmentId);
	}
	
	@Override
	protected Collection<Permissible> initialize(String rootId) {
		Collection<Permissible> permissibles = new ArrayList<Permissible>();
		
		ResourceID resource = new ResourceID(Department.class, rootId);
		Permission permission = new Permission(resource, new Privilege(AccessLevel.MANAGE));
						
		permissibles.add(permission);
		return permissibles;
	}
	
}
