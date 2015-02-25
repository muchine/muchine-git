package com.lgu.abc.core.mybatis.handlers.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.mybatis.handlers.query.impl.CompanyQueryResolver;

@Component
public class QueryResolverFactory {

	private final List<QueryResolver> resolvers = new ArrayList<QueryResolver>();;
	
	protected QueryResolverFactory() {
		initialize();
	}
	
	public synchronized void add(QueryResolver resolver) {
		resolvers.add(resolver);
	}
	
	public List<QueryResolver> create() {
		return resolvers;
	}
	
	private void initialize() {
		resolvers.add(new CompanyQueryResolver());
	}
	
}
