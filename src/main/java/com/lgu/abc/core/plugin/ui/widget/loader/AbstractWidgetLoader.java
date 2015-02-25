package com.lgu.abc.core.plugin.ui.widget.loader;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractWidgetLoader extends AbstractModulePluggable implements WidgetLoadable {

	protected AbstractWidgetLoader(String moduleId) {
		super(moduleId);
	}

	@Override
	public final int order() {
		return 0;
	}

}
