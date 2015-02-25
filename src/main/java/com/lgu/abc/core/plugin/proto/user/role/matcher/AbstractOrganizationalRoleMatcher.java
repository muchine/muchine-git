package com.lgu.abc.core.plugin.proto.user.role.matcher;

import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;
import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRoleMatchable;

public abstract class AbstractOrganizationalRoleMatcher implements OrganizationalRoleMatchable {

	private final OrganizationalRole role;
	
	protected AbstractOrganizationalRoleMatcher(OrganizationalRole role) {
		this.role = role;
	}
	
	@Override
	public final OrganizationalRole role() {
		return role;
	}

}
