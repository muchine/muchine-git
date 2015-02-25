package com.lgu.abc.core.web.security.filter.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.base.operation.Action;
import com.lgu.abc.core.web.security.filter.Filter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public abstract class ActionFilter<T extends ActionEntity, Q> implements Filter<T, Q> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public final void filterEntity(T entity) throws InvalidParameterException {
		Action action = entity.getAction();
		logger.trace("action: " + action);
		
		if (action.isCreating())
			create(entity);
		else if (action.isUpdating())
			update(entity);
		else if (action.isDeleting()) 
			delete(entity);
		else 
			logger.info("can't recognize action: " + entity.getAction());
	}
	
	@Override
	public void filterQuery(Q query) throws InvalidParameterException {
		
	}

	protected abstract void create(T entity) throws InvalidParameterException;
	
	protected abstract void update(T entity) throws InvalidParameterException;
	
	protected abstract void delete(T entity) throws InvalidParameterException;

}
