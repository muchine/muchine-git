package com.lgu.abc.core.common.spec;

import org.apache.commons.lang.Validate;

public class OrSpecification extends CompositeSpecification {

	private final Specification one;
	
	private final Specification other;
	
	public OrSpecification(Specification one, Specification other) {
		Validate.notNull(one, "first specification is null.");
		Validate.notNull(other, "other specification is null.");
		
		this.one = one;
		this.other =other;
	}
	
	@Override
	public boolean isSatisfiedBy(Object object) {
		return one.isSatisfiedBy(object) || other.isSatisfiedBy(object);
	}

}
