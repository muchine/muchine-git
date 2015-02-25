package com.lgu.abc.core.plugin.ui.feed.load;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractNewsFeedLoader extends AbstractModulePluggable implements NewsFeedLoadable {
	
	protected AbstractNewsFeedLoader(NewsFeedLoaderRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}
	
	@Override
	public String query() {
		/*
		 * Type string that ends with 99 represents notification count query so this should be excluded from query result.
		 * The normal type string of a feed includes a string like '01', '02', i.e 'APR_01', "APR_12'
		 */
		StringBuilder builder = new StringBuilder();
		builder.append(type());
		
		if (!type().endsWith("_")) builder.append("_");
		builder.append("[0-8].");
		
		return builder.toString();
	}
	
}
