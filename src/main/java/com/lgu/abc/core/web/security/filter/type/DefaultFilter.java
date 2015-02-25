package com.lgu.abc.core.web.security.filter.type;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.filter.base.ActionFilter;

public class DefaultFilter<T extends RootEntity, Q extends BaseEntity> extends ActionFilter<T, Q> {

	@Override
	protected void create(T entity) {
		entity.setId(null);
	}

	@Override
	protected void update(T entity) {
		
	}

	@Override
	protected void delete(T entity) {
		
	}
	
}
