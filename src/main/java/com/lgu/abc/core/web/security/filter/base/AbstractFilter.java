package com.lgu.abc.core.web.security.filter.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.web.security.filter.Filter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public abstract class AbstractFilter<T, Q> implements Filter<T, Q> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void filterEntity(T entity) throws InvalidParameterException {
		
	}

	@Override
	public void filterQuery(Q query) throws InvalidParameterException {
		
	}
	
}
