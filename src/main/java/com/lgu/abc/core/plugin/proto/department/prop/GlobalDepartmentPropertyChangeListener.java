package com.lgu.abc.core.plugin.proto.department.prop;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class GlobalDepartmentPropertyChangeListener implements DepartmentPropertyChangeListenable {

	private static final Set<DepartmentPropertyChangeListenable> listenables = new HashSet<DepartmentPropertyChangeListenable>();
	
	public synchronized void add(DepartmentPropertyChangeListenable listenable) {
		listenables.add(listenable);
	}
	
	@Override
	public void nameChanged(Department department) {
		for (DepartmentPropertyChangeListenable listenable : listenables) {
			listenable.nameChanged(department);
		}
	}

	@Override
	public void parentChanged(User actor, Department oldDepartment, Department newDepartment) {
		for (DepartmentPropertyChangeListenable listenable : listenables) {
			listenable.parentChanged(actor, oldDepartment, newDepartment);
		}
	}

}
