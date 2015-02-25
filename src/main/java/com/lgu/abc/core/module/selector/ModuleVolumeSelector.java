package com.lgu.abc.core.module.selector;

import com.lgu.abc.core.module.Module;

public class ModuleVolumeSelector implements ModuleSelectable {

	private static final ModuleVolumeSelector instance = new ModuleVolumeSelector();
		
	private ModuleVolumeSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return module.volume().isSupported();
	}
	
	public static ModuleVolumeSelector instance() {
		return instance;
	}

}
