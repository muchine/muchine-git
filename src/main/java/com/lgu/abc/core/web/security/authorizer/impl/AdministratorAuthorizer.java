package com.lgu.abc.core.web.security.authorizer.impl;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.authorizer.Authorizer;

public class AdministratorAuthorizer<T extends RootEntity, Q extends BaseEntity> implements Authorizer<T, Q> {
		
	@Override
	public boolean authorizeCreation(T entity) {
		return entity.getActor().canManage(entity);
	}

	@Override
	public boolean authorizeUpdate(T entity) {
		return entity.getActor().canManage(entity);
	}

	@Override
	public boolean authorizeDeletion(T entity) {
		return entity.getActor().canManage(entity);
	}

	@Override
	public boolean authorizeEntity(T entity, T found) {
		return entity.getActor().canRead(found);
	}

	@Override
	public boolean authorizeQuery(Q query, Iterable<T> found) {
		for (T entity : found) {
			if (!query.getActor().canRead(entity)) return false;
		}
		
		return true;
	}

}
