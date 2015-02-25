package com.lgu.abc.core.plugin.ui.feed.content;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;
import com.lgu.abc.core.common.vo.Name;

public abstract class AbstractContentProvidable extends AbstractModulePluggable implements ContentProvidable {

	protected AbstractContentProvidable(String moduleId) {
		super(moduleId);
		ContentProviderRegistry.add(this);
	}

	@Override
	public final Name name() {
		return null;
	}

	@Override
	public final int order() {
		return 0;
	}

}
