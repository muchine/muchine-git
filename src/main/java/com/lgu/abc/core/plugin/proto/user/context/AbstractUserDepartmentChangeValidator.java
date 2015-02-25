package com.lgu.abc.core.plugin.proto.user.context;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;
import com.lgu.abc.core.common.vo.Name;

public abstract class AbstractUserDepartmentChangeValidator extends AbstractModulePluggable implements UserDepartmentChangeValidatable {

	protected AbstractUserDepartmentChangeValidator(UserDepartmentChangeValidatorRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}
	
	@Override
	public final Name name() {
		return null;
	}

}
