package com.lgu.abc.core.plugin.proto.user.role.matcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public class UserRoleMatcher extends AbstractOrganizationalRoleMatcher {

	private final List<User> users = new ArrayList<User>();
	
	public UserRoleMatcher(OrganizationalRole role, Iterable<User> users) {
		super(role);
		
		if (IterableUtils.isEmpty(users)) return;
		CollectionUtils.addAll(this.users, users.iterator());
	}
	
	@Override
	public boolean matches(User user, Department department) {
		for (User assigned : users) {
			if (assigned.identical(user)) return true;
		}
		
		return false;
	}

}
