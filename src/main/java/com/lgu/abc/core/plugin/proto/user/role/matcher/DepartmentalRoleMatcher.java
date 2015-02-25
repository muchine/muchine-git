package com.lgu.abc.core.plugin.proto.user.role.matcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lgu.abc.core.plugin.proto.department.role.DepartmentRoleMapper;
import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public class DepartmentalRoleMatcher extends AbstractOrganizationalRoleMatcher {

	private final List<DepartmentRoleMapper> maps = new ArrayList<DepartmentRoleMapper>();
	
	public <T extends DepartmentRoleMapper> DepartmentalRoleMatcher(OrganizationalRole role, Iterable<T> maps) {
		super(role);
		CollectionUtils.addAll(this.maps, maps.iterator());
	}
	
	@Override
	public boolean matches(User user, Department department) {
		for (DepartmentRoleMapper map : maps) {
			if (map.matches(user, department)) return true;
		}
		
		return false;
	}

}
