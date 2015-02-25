package com.lgu.abc.core.web.security.authorizer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.authorizer.Authorizer;

public class LabelAuthorizer<T extends RootEntity, Q extends BaseEntity> implements Authorizer<T, Q> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public boolean authorizeCreation(T entity) {
		return entity.getActor().canWrite(entity);
	}

	/*
	 * 도메인 객체가 여러 개의 레이블을 가지는 경우, 보통은 어떤 사용자가 다른 사용자가 소유한 도메인 객체를 수정할 수는 없다.
	 * 따라서 이 메소드가 타게 되는 경우는 자신이 등록한 도메인 객체에 레이블을 추가하게 되는 경우인데, 이 경우 쓰기 권한이 있으면 해당 레이블을
	 * 부착할 수 있는 것으로 간주한다.
	 */
	@Override
	public boolean authorizeUpdate(T entity) {
		return entity.getActor().canWrite(entity);
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
		for (T result : found) {
			result.setActor(query.getActor());
			if (!query.getActor().canRead(result)) return false;
		}
		
		return true;
	}

}
