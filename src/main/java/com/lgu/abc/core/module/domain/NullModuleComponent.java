package com.lgu.abc.core.module.domain;

public class NullModuleComponent extends ModuleComponent {

	private static final ModuleComponent instance = new NullModuleComponent();
	
	private NullModuleComponent() {
		super(false, "");
	}
	
	public static ModuleComponent instance() {
		return instance;
	}
	
}
