package com.lgu.abc.core.support.populator.impl.list;

import java.util.List;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.support.populator.AbstractPopulator;
import com.lgu.abc.core.support.populator.Populator;

public abstract class AbstractListPopulator extends AbstractPopulator implements Populator {

	@SuppressWarnings("unchecked")
	@Override
	public boolean canPopulate(Object source, Object target) {
		if (!super.canPopulate(source, target)) return false;
		
		if (!(target instanceof List)) {
			logger.trace("target is not list type.");
			return false;
		}
		
		if (((List<Object>) source).isEmpty()) {
			logger.trace("source list is empty.");
			return false;
		}
		
		return true;
	}
	
	protected boolean isIdentifiableList(List<Object> source) {
		for (Object element : source) {
			if (element == null) continue;
			return element instanceof Identifiable;
		}
		
		throw new IllegalArgumentException("source list has null elements.");
	}
		
}
