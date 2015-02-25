package com.lgu.abc.core.module;

public class ModuleUtils {

	public static boolean isEnabled(Module module) {
		return module.isOpened() && module.isEnabled();
	}
	
}
