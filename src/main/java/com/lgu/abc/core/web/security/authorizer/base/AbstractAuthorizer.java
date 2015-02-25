package com.lgu.abc.core.web.security.authorizer.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.web.security.authorizer.Authorizer;

public abstract class AbstractAuthorizer<T, Q> implements Authorizer<T, Q> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public boolean authorizeCreation(T entity) {
		return true;
	}

	@Override
	public boolean authorizeUpdate(T entity) {
		return true;
	}

	@Override
	public boolean authorizeDeletion(T entity) {
		return true;
	}

	@Override
	public boolean authorizeEntity(T entity, T found) {
		return true;
	}

	@Override
	public boolean authorizeQuery(Q query, Iterable<T> found) {
		return true;
	}

}
