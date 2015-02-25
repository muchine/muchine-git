package com.lgu.abc.core.support.populator.impl;

import com.lgu.abc.core.support.populator.AbstractPopulator;

public class IdPopulator extends AbstractPopulator {

	@Override
	public Object populate(Object source, Object target) {
		return source;
	}

	@Override
	public boolean canPopulate(Object source, Object target) {
		if (source == null || target == null) return false;
		return source instanceof Long && target instanceof Long;
	}

}
