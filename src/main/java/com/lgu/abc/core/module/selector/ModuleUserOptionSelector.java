package com.lgu.abc.core.module.selector;

import com.lgu.abc.core.module.Module;

public class ModuleUserOptionSelector implements ModuleSelectable {

	private static final ModuleUserOptionSelector instance = new ModuleUserOptionSelector();
	
	private ModuleUserOptionSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return module.option().getUserOption().isSupported();
	}
	
	public static ModuleUserOptionSelector instance() {
		return instance;
	}

}
