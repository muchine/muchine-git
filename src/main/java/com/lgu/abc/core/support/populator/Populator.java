package com.lgu.abc.core.support.populator;

public interface Populator {

	public Object populate(Object source, Object target);
	
	public boolean canPopulate(Object source, Object target);
	
}
