package com.lgu.abc.core.mybatis.handlers.query;

public interface QueryResolver {

	void resolve(Object query);
	
	boolean canResolve(Object query);
	
}
