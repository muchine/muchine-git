package com.lgu.abc.core.common.spec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CompositeSpecification implements Specification {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public abstract boolean isSatisfiedBy(Object object);

	@Override
	public Specification and(Specification spec) {
		return new AndSpecification(this, spec);
	}

	@Override
	public Specification or(Specification spec) {
		return new OrSpecification(this, spec);
	}

	@Override
	public Specification not() {
		return new NotSpecification(this);
	}

}
