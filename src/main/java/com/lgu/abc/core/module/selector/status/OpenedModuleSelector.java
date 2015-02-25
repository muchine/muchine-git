package com.lgu.abc.core.module.selector.status;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.selector.ModuleSelectable;

public class OpenedModuleSelector implements ModuleSelectable {

	private static final OpenedModuleSelector instance = new OpenedModuleSelector();
	
	private OpenedModuleSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return module.isOpened();
	}
	
	public static OpenedModuleSelector instance() {
		return instance;
	}

}
