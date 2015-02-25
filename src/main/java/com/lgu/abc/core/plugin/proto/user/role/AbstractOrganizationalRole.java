package com.lgu.abc.core.plugin.proto.user.role;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractOrganizationalRole extends AbstractModulePluggable implements OrganizationalRole {

	protected AbstractOrganizationalRole(OrganizationalRoleRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}

	@Override
	public boolean isDefault() {
		return false;
	}

}
