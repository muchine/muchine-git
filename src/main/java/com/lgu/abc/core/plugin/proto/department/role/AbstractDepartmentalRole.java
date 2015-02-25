package com.lgu.abc.core.plugin.proto.department.role;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractDepartmentalRole extends AbstractModulePluggable implements DepartmentalRole {

	protected AbstractDepartmentalRole(GlobalDepartmentRoleManager assigner, String moduleId) {
		super(moduleId);
		assigner.add(this);
	}

	@Override
	public boolean isDefault() {
		return false;
	}
	
}
