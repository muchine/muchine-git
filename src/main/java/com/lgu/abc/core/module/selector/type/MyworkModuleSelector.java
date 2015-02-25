package com.lgu.abc.core.module.selector.type;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.domain.ModuleType;
import com.lgu.abc.core.module.selector.ModuleSelectable;

public class MyworkModuleSelector implements ModuleSelectable {

	private static final MyworkModuleSelector instance = new MyworkModuleSelector();
	
	private MyworkModuleSelector() {}
	
	@Override
	public boolean canSelect(Module module) {
		return ModuleType.MYWORK.equals(module.type());
	}
	
	public static MyworkModuleSelector instance() {
		return instance;
	}

}
