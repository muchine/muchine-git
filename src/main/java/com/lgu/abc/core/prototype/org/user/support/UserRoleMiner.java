package com.lgu.abc.core.prototype.org.user.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.core.base.security.role.department.DepartmentManager;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public class UserRoleMiner {
	
	protected static final Log logger = LogFactory.getLog(UserRoleMiner.class);
	
	public static boolean isCompanyAdministrator(User user) {
		return user.hasRole(new CompanyAdministrator(user.getCompany().getId()));
	}
	
	public static boolean isDepemartmentManager(User user) {
		return user.hasRole(new DepartmentManager(user.getDepartment().getId()));
	}
	
	public static boolean isDepemartmentManager(User user, Department department) {
		return user.hasRole(new DepartmentManager(department.getId()));
	}
	
}
