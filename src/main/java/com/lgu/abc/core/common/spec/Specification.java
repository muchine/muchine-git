package com.lgu.abc.core.common.spec;

public interface Specification {

	boolean isSatisfiedBy(Object object);
	
	Specification and(Specification spec);
	
	Specification or(Specification spec);
	
	Specification not();
	
}
