package com.lgu.abc.core.support.populator.impl;

import com.lgu.abc.core.support.populator.Populator;

public class NullPopulator implements Populator {

	@Override
	public Object populate(Object source, Object target) {
		return target;
	}

	@Override
	public boolean canPopulate(Object source, Object target) {
		return true;
	}

}
