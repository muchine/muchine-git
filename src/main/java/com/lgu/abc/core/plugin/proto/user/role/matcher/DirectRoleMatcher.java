package com.lgu.abc.core.plugin.proto.user.role.matcher;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public class DirectRoleMatcher extends AbstractOrganizationalRoleMatcher {

	private final AbstractRole permissible;
	
	public DirectRoleMatcher(OrganizationalRole role, AbstractRole permissible) {
		super(role);
		this.permissible = permissible;
	}
	
	@Override
	public boolean matches(User user, Department department) {
		return user.hasRole(permissible);
	}

}
