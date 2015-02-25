package com.lgu.abc.core.mybatis.handlers.query.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.domain.query.ResolvableQuery;
import com.lgu.abc.core.mybatis.handlers.query.QueryResolver;
import com.lgu.abc.core.mybatis.handlers.query.QueryResolverFactory;

@Component
public class GenericQueryResolver implements QueryResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private QueryResolverFactory factory;
	
	/**
	 * Adjust query for workspaces and shared items
	 * 
	 * @param query
	 */
	@Override
	public void resolve(Object query) {
		if (query != null && query instanceof ResolvableQuery) {
			ResolvableQuery casted = (ResolvableQuery) query;
			
			// initialize workspace and shared query for security
			casted.clearResolver();
		}
		
		for (QueryResolver resolver : factory.create()) resolver.resolve(query);
	}

	@Override
	public boolean canResolve(Object query) {
		return true;
	}
	
}
