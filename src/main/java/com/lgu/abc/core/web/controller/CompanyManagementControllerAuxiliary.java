package com.lgu.abc.core.web.controller;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.web.security.authorizer.impl.AdministratorAuthorizer;
import com.lgu.abc.core.web.security.filter.Filter;

public class CompanyManagementControllerAuxiliary<T extends RootEntity, Q extends BaseEntity> extends ControllerAuxiliary<T, Q> {

	public CompanyManagementControllerAuxiliary() {
		super(new AdministratorAuthorizer<T, Q>());
	}
	
	public CompanyManagementControllerAuxiliary(Filter<T, Q> filter) {
		super(new AdministratorAuthorizer<T, Q>(), filter);
	}
	
}
