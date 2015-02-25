package com.lgu.abc.core.plugin.ui.feed.embed;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractNewsFeedEmbedder extends AbstractModulePluggable implements NewsFeedEmbeddable {
	
	protected AbstractNewsFeedEmbedder(NewsFeedEmbedderRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}
	
}
