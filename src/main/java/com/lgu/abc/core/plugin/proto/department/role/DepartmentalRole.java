package com.lgu.abc.core.plugin.proto.department.role;

import java.util.List;

import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public interface DepartmentalRole extends OrganizationalRole {
	
	void assign(User actor, List<User> users);
	
	void withdraw(User actor, User user, Department department);
	
	int maxAssignableCount();
	
}
