package com.lgu.abc.core.module.domain;

public class NullModuleOption extends ModuleOption {

	private static final ModuleOption instance = new NullModuleOption();
	
	private NullModuleOption() {
		super(NullModuleComponent.instance(), NullModuleComponent.instance());
	}
	
	public static ModuleOption instance() {
		return instance;
	}

}
