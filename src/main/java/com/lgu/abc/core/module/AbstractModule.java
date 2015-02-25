package com.lgu.abc.core.module;

import lombok.ToString;

import com.lgu.abc.core.module.domain.ModuleRenderer;

@ToString
public abstract class AbstractModule implements Module {

	private final String id;
	
	private final boolean opened;
	
	private final boolean enabled;
	
	protected AbstractModule(String id, boolean opened, boolean enabled) {
		this.id = id;
		this.opened = opened;
		this.enabled = enabled;
	}
	
	@Override
	public final String id() {
		return id;
	}

	@Override
	public final boolean isOpened() {
		return opened;
	}

	@Override
	public final boolean isEnabled() {
		return enabled;
	}

	@Override
	public ModuleRenderer render() {
		return new ModuleRenderer(true);
	}

}
