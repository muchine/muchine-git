package com.lgu.abc.core.web.security.authorizer.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.web.security.authorizer.Authorizer;

public abstract class BlockingAuthorizer<T, Q> implements Authorizer<T, Q> {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean authorizeCreation(T entity) {
		return false;
	}

	@Override
	public boolean authorizeUpdate(T entity) {
		return false;
	}

	@Override
	public boolean authorizeDeletion(T entity) {
		return false;
	}

	@Override
	public boolean authorizeEntity(T entity, T found) {
		return false;
	}

	@Override
	public boolean authorizeQuery(Q query, Iterable<T> found) {
		return false;
	}
	
}
