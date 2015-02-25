package com.lgu.abc.core.plugin.proto.user.role;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public interface OrganizationalRoleMatchable {

	boolean matches(User user, Department department);
	
	OrganizationalRole role();
	
}
