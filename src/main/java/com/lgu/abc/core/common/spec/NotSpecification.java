package com.lgu.abc.core.common.spec;

import org.apache.commons.lang.Validate;

public class NotSpecification extends CompositeSpecification {

	private final Specification spec;
	
	public NotSpecification(Specification spec) {
		Validate.notNull(spec, "specification is null.");
		this.spec = spec;
	}
	
	@Override
	public boolean isSatisfiedBy(Object object) {
		return !spec.isSatisfiedBy(object);
	}

}
