package com.lgu.abc.core.mybatis.handlers.query.impl;

import com.lgu.abc.core.base.domain.query.ResolvableQuery;
import com.lgu.abc.core.mybatis.handlers.query.AbstractQueryResolver;
import com.lgu.abc.core.mybatis.handlers.query.QueryResolver;

public class CompanyQueryResolver extends AbstractQueryResolver implements
		QueryResolver {

	@Override
	public void resolve(Object query) {
		if (!canResolve(query)) return;
		
		ResolvableQuery casted = (ResolvableQuery) query;
		
		casted.resolveCompany(casted.getActor().getCompany().getId());
	}

}
