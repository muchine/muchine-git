package com.lgu.abc.core.web.security.filter.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.web.security.filter.Filter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public abstract class ChainableFilter<T, Q> implements Filter<T, Q> {

	private List<Filter<T, Q>> chain = new ArrayList<Filter<T, Q>>();
	
	public ChainableFilter<T, Q> add(Filter<T, Q> filter) {
		chain.add(filter);
		return this;
	}

	@Override
	public final void filterEntity(T entity) throws InvalidParameterException {
		Validate.notEmpty(chain, "filter chain is empty.");
		for (Filter<T, Q> filter : chain) filter.filterEntity(entity);
	}

	@Override
	public final void filterQuery(Q query) throws InvalidParameterException {
		Validate.notEmpty(chain, "filter chain is empty.");
		for (Filter<T, Q> filter : chain) filter.filterQuery(query);		
	}
		
}
