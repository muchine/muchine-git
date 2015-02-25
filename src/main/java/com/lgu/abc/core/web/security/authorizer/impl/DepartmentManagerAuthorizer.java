package com.lgu.abc.core.web.security.authorizer.impl;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.prototype.org.user.support.UserRoleMiner;
import com.lgu.abc.core.web.security.authorizer.Authorizer;

public class DepartmentManagerAuthorizer<T extends RootEntity, Q extends BaseEntity> implements Authorizer<T, Q> {

	@Override
	public boolean authorizeCreation(T entity) {
		return authorize(entity);
	}

	@Override
	public boolean authorizeUpdate(T entity) {
		return authorize(entity);
	}

	@Override
	public boolean authorizeDeletion(T entity) {
		return authorize(entity);
	}

	@Override
	public boolean authorizeEntity(T entity, T found) {
		return authorize(entity);
	}

	@Override
	public boolean authorizeQuery(Q query, Iterable<T> found) {
		return authorize(query);
	}
	
	private boolean authorize(BaseEntity entity) {
		return UserRoleMiner.isDepemartmentManager(entity.getActor());
	}

}
