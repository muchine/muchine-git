package com.lgu.abc.core.support.populator.impl.list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.support.populator.Populator;
import com.lgu.abc.core.support.populator.PopulatorFactory;

public class IdentifiableListPopulator extends AbstractListPopulator implements Populator {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private final PopulatorFactory factory;
	
	public IdentifiableListPopulator(PopulatorFactory factory) {
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object populate(Object sourceList, Object targetList) {
		if (!canPopulate(sourceList, targetList)) return targetList;
		
		List<Object> sources = (List<Object>) sourceList;
		List<Object> targets = (List<Object>) targetList;
		
		for (Object source : sources) {
			if (source == null) continue;
			
			Identifiable identified = (Identifiable) source;
			Object element = getIdenticalElement(identified, targets);
			
			if (element != null) {
				logger.trace("populate element of list... source: " + source);
				factory.create().populate(identified, element);
			}
			else if(shouldAdd(identified)) {
				addSourceToTargetList(source, targets);
			}
		}
		
		return targetList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canPopulate(Object source, Object target) {
		return super.canPopulate(source, target) && isIdentifiableList((List<Object>) source);
	}
	
	private Object getIdenticalElement(Identifiable entity, List<?> objects) {
		if (objects == null) return null;
		
		for (Object e : objects) {
			if (e == null) continue;
			
			Identifiable casted = (Identifiable) e;
			if (casted.identical(entity)) return e;
		}
		
		return null;
	}
	
	private void addSourceToTargetList(Object source, List<Object> targets) {
		logger.trace("add element to the list of " + source.getClass() + ". source: " + source);
		targets.add(source);
	}
	
	protected boolean shouldAdd(Identifiable source) {
		return true;
	}

}
