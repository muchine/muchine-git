package com.lgu.abc.core.web.security.filter;

import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

/**
 * Filters invalid request parameters from business perspective. 
 * 
 * @author sejoon
 *
 * @param <T>
 * @param <Q>
 */
public interface Filter<T, Q > {
	
	void filterEntity(T entity) throws InvalidParameterException;
	
	void filterQuery(Q query) throws InvalidParameterException;
	
}
