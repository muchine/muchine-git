package com.lgu.abc.core.mybatis.handlers.query;

import com.lgu.abc.core.base.domain.query.ResolvableQuery;

public abstract class AbstractQueryResolver implements QueryResolver {
	
	public boolean canResolve(Object query) {
		if (query == null || !(query instanceof ResolvableQuery)) return false;
		
		ResolvableQuery casted = (ResolvableQuery) query;
		return casted.isResolving();
	}
	
}
