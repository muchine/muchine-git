package com.lgu.abc.core.plugin.proto.user.role.selector;

import com.lgu.abc.core.plugin.proto.user.role.OrganizationalRole;

public interface OrganizationalRoleSelectable {

	boolean canSelect(OrganizationalRole role);
	
}
