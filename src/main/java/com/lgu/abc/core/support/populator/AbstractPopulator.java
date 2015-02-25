package com.lgu.abc.core.support.populator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractPopulator implements Populator {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean canPopulate(Object source, Object target) {
		if (source == null || target == null) {
			logger.trace("source or target is null. source: " + source + ", target: " + target);
			return false;
		}
		
		// Use assignable method to compare proxy class when lazy loading is enabled.
		if (!target.getClass().isAssignableFrom(source.getClass())) {
			logger.trace("class are different. source: " + source.getClass() + ", target: " + target.getClass());
			return false;
		}
		
		if (source == target) {
			logger.trace("source and target are identical.");
			return false;
		}
		
		return true;
	}

}
