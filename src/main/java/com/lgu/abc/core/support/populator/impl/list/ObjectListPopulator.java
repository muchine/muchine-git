package com.lgu.abc.core.support.populator.impl.list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.support.populator.Populator;

public class ObjectListPopulator extends AbstractListPopulator implements Populator {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public Object populate(Object sourceList, Object targetList) {
		if (!canPopulate(sourceList, targetList)) return targetList;
		
		List<Object> sources = (List<Object>) sourceList;
		List<Object> targets = (List<Object>) targetList;
		
		if (targets.size() > 0) {
			logger.trace("source is not an instance of action entity and target list has already " + targets.size() + " elements. skip population...");
			return targetList;
		}

		logger.trace("add element to the target list...");
		targets.addAll(sources);
		
		return targetList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canPopulate(Object source, Object target) {
		return super.canPopulate(source, target) && !isIdentifiableList((List<Object>) source);
	}

}
