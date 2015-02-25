package com.lgu.abc.core.plugin.volume;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractModuleVolume extends AbstractModulePluggable implements ModuleVolume {

	protected AbstractModuleVolume(ModuleVolumeRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}

}
