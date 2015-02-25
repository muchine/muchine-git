package com.lgu.abc.core.web.browser;

import com.lgu.abc.core.common.spec.CompositeSpecification;

public abstract class BrowserSpecification extends CompositeSpecification {
	
	private final String version;
	
	protected BrowserSpecification(String version) {
		this.version = version;
	}

	@Override
	public boolean isSatisfiedBy(Object object) {
		return object.toString().contains(version);
	}

	public String version() {
		return version;
	}
	
	public boolean identical(BrowserSpecification spec) {
		return getClass().equals(spec.getClass());
	}
	
}
