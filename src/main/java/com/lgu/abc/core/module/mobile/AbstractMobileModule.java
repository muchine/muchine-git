package com.lgu.abc.core.module.mobile;

public abstract class AbstractMobileModule implements MobileModule {

	protected AbstractMobileModule(MobileModuleRegistry registry) {
		registry.add(this);
	}

}
