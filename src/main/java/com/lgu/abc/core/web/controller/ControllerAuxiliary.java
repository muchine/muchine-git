package com.lgu.abc.core.web.controller;

import lombok.Data;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.authorizer.Authorizer;
import com.lgu.abc.core.web.security.authorizer.common.BypassAuthorizer;
import com.lgu.abc.core.web.security.filter.Filter;
import com.lgu.abc.core.web.security.filter.type.DefaultFilter;

public @Data class ControllerAuxiliary<T extends RootEntity, Q extends BaseEntity> {

	private final Authorizer<T, Q> authorizer;
	
	private final Filter<T, Q> filter;
	
	public ControllerAuxiliary() {
		this(new BypassAuthorizer<T, Q>());
	}
		
	public ControllerAuxiliary(Authorizer<T, Q> authorizer) {
		this(authorizer, new DefaultFilter<T, Q>());
	}
	
	public ControllerAuxiliary(Authorizer<T, Q> authorizer, Filter<T, Q> filter) {
		this.authorizer = authorizer;
		this.filter = filter;
	}
		
}
