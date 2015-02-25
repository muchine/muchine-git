package com.lgu.abc.core.module.selector.type;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.domain.ModuleType;
import com.lgu.abc.core.module.selector.ModuleSelectable;

public class SupportModuleSelector implements ModuleSelectable {

	private static final SupportModuleSelector instance = new SupportModuleSelector();
	
	private SupportModuleSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return ModuleType.SUPPORT.equals(module.type());
	}
	
	public static SupportModuleSelector instance() {
		return instance;
	}

}
