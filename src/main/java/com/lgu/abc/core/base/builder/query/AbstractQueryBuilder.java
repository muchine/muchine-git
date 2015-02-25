package com.lgu.abc.core.base.builder.query;

public abstract class AbstractQueryBuilder<Q, B> implements QueryBuildable<Q> {
		
	protected abstract B builder();
	
}
