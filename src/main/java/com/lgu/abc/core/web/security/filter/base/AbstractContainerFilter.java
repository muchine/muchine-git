package com.lgu.abc.core.web.security.filter.base;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public abstract class AbstractContainerFilter<T extends ActionEntity, Q> extends ActionFilter<T, Q> {

	@Override
	protected final void create(T entity) throws InvalidParameterException {
		populate(entity);
	}

	@Override
	protected final void update(T entity) throws InvalidParameterException {
		populate(entity);
	}

	@Override
	protected final void delete(T entity) throws InvalidParameterException {

	}
	
	protected abstract void populate(T entity) throws InvalidParameterException;

}
