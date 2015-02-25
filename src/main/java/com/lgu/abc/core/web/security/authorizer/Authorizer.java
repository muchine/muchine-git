package com.lgu.abc.core.web.security.authorizer;


public interface Authorizer<T, Q> {
	
	boolean authorizeCreation(T entity);
	
	boolean authorizeUpdate(T entity);
	
	boolean authorizeDeletion(T entity);
	
	boolean authorizeEntity(T entity, T found);
	
	boolean authorizeQuery(Q query, Iterable<T> found);
	
}
