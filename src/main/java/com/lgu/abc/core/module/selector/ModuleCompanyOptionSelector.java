package com.lgu.abc.core.module.selector;

import com.lgu.abc.core.module.Module;

public class ModuleCompanyOptionSelector implements ModuleSelectable {

	private static final ModuleCompanyOptionSelector instance = new ModuleCompanyOptionSelector();
	
	private ModuleCompanyOptionSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return module.option().getCompanyOption().isSupported();
	}
	
	public static ModuleCompanyOptionSelector instance() {
		return instance;
	}

}
