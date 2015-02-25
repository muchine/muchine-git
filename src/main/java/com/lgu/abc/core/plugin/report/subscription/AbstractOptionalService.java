package com.lgu.abc.core.plugin.report.subscription;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractOptionalService extends AbstractModulePluggable implements OptionalService {

	protected AbstractOptionalService(OptionalServiceRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}

}
