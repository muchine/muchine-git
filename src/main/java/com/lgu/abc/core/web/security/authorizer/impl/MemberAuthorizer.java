package com.lgu.abc.core.web.security.authorizer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.authorizer.Authorizer;

/**
 * A user can create, update, delete and read entities in the container like company, workspace of which she is a member. 
 * @author Sejoon
 *
 * @param <T>
 * @param <Q>
 */
public class MemberAuthorizer<T extends RootEntity, Q extends BaseEntity> implements Authorizer<T, Q> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public boolean authorizeCreation(T entity) {
		return entity.getActor().canRead(entity);
	}

	@Override
	public boolean authorizeUpdate(T entity) {
		return entity.getActor().canRead(entity);
	}

	@Override
	public boolean authorizeDeletion(T entity) {
		return entity.getActor().canRead(entity);
	}

	@Override
	public boolean authorizeEntity(T entity, T found) {
		return entity.getActor().canRead(found);
	}

	@Override
	public boolean authorizeQuery(Q query, Iterable<T> found) {
		// Consider adding privilege checking logic here because it might bring significant performance degradation.
		for (T result : found) {
			result.setActor(query.getActor());
//			if (!query.getActor().canRead(result)) return false;
		}
		
		return true;
	}
	
}
