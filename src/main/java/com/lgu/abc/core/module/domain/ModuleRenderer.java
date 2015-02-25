package com.lgu.abc.core.module.domain;

import lombok.Data;

public @Data class ModuleRenderer {

	private final boolean hiddenIfDisabled;
	
	public ModuleRenderer(boolean hiddenIfDisabled) {
		this.hiddenIfDisabled = hiddenIfDisabled;
	}
	
}
