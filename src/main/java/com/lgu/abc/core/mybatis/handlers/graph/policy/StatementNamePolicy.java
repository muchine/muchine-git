package com.lgu.abc.core.mybatis.handlers.graph.policy;

public interface StatementNamePolicy {
	
	String insert(Class<?> type, String field);
	
	String update(Class<?> type, String field);
	
	String delete(Class<?> type, String field);
	
	String findAll(Class<?> type, String field);
	
	String deleteAll(Class<?> type, String field);
	
	String getNamespace(Class<?> parent, String fieldName);
	
}
