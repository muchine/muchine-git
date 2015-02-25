package com.lgu.abc.core.plugin.proto.department.role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.plugin.proto.user.context.GlobalUserDepartmentChangeListener;
import com.lgu.abc.core.plugin.proto.user.context.UserDepartmentChangeListenable;
import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRoleRegistry;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.SystemUser;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.support.DepartmentContextUtils;

@Component
public class GlobalDepartmentRoleManager implements UserDepartmentChangeListenable {

	private static final Set<DepartmentalRole> assignables = new HashSet<DepartmentalRole>();
	
	@Autowired
	private OrganizationalRoleRegistry registry;
	
	@Autowired
	private GlobalDepartmentRoleManager(GlobalUserDepartmentChangeListener listener) {
		listener.add(this);
	}
	
	public synchronized void add(DepartmentalRole assignable) {
		PluggableUtils.add(assignable, assignables);
		registry.add(assignable);
	}
	
	public static DepartmentalRole find(String assignableId, Company company) {
		return PluggableUtils.find(assignableId, company, assignables);
	}
	
	public static List<DepartmentalRole> enabled(Company company) {
		return PluggableUtils.enabled(company, assignables);
	}
	
	public static Set<DepartmentalRole> all() {
		return PluggableUtils.all(assignables);
	}

	@Override
	public void changing(User oldUser, User newUser) throws CoreException {
		
	}

	@Override
	public void changed(User oldUser, User newUser) {
		List<Department> movedOut = DepartmentContextUtils.getMovedOutDepartments(oldUser, newUser);
		
		User actor = SystemUser.create(newUser.getCompany());
		for (Department department : movedOut) {
			for (DepartmentalRole assignable : assignables) {
				assignable.withdraw(actor, newUser, department);
			}
		}
	}

}
