package com.lgu.abc.core.plugin.proto.user.role.selector;

import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;

public class OrganizationalRoleDefaultSelector implements OrganizationalRoleSelectable {

	private static final OrganizationalRoleDefaultSelector instance = new OrganizationalRoleDefaultSelector();
	
	private OrganizationalRoleDefaultSelector() {}
	
	@Override
	public boolean canSelect(OrganizationalRole role) {
		return role.isDefault();
	}
	
	public static OrganizationalRoleDefaultSelector instance() {
		return instance;
	}

}
