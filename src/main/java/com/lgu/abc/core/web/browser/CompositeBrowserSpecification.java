package com.lgu.abc.core.web.browser;

import com.lgu.abc.core.common.spec.Specification;

public class CompositeBrowserSpecification extends BrowserSpecification {

	private final Specification specification;
	
	CompositeBrowserSpecification(Specification specification) {
		super("");
		this.specification = specification;
	}
		
	@Override
	public boolean isSatisfiedBy(Object object) {
		return specification.isSatisfiedBy(object);
	}

}
