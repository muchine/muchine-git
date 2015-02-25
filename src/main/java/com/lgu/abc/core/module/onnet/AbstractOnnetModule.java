package com.lgu.abc.core.module.onnet;

public abstract class AbstractOnnetModule implements OnnetModule {

	protected AbstractOnnetModule(OnnetModuleRegistry registry) {
		registry.add(this);
	}	

}
