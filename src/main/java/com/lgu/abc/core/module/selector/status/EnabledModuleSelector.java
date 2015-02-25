package com.lgu.abc.core.module.selector.status;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.selector.ModuleSelectable;

public class EnabledModuleSelector implements ModuleSelectable {

	private static final EnabledModuleSelector instance = new EnabledModuleSelector();
	
	private EnabledModuleSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return module.isOpened() && module.isEnabled();
	}
	
	public static EnabledModuleSelector instance() {
		return instance;
	}

}
