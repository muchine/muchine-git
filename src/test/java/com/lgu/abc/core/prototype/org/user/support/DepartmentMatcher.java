package com.lgu.abc.core.prototype.org.user.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.test.common.base.matchers.Matcher;

public class DepartmentMatcher implements Matcher<Department> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final User user;
	
	public DepartmentMatcher(User user) {
		this.user = user;
	}
	
	@Override
	public boolean matches(Department object) {
		logger.trace("department: " + object.getId() + ", user: " + user);
		return user.belongsToDepartment(object.getId());
	}
	
}
