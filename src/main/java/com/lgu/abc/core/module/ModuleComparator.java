package com.lgu.abc.core.module;

import java.util.Comparator;

public class ModuleComparator implements Comparator<Module> {

	private static final ModuleComparator instance = new ModuleComparator();
	
	private ModuleComparator() {}
	
	@Override
	public int compare(Module o1, Module o2) {
		return o1.order() - o2.order();
	}
	
	public static ModuleComparator instance() {
		return instance;
	}
	
}
