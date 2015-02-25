package com.lgu.abc.core.plugin.proto.department.prop;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public interface DepartmentPropertyChangeListenable {

	void nameChanged(Department department);
	
	void parentChanged(User actor, Department oldDepartment, Department newDepartment);
	
}
