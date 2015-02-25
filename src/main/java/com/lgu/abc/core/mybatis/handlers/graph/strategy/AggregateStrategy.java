package com.lgu.abc.core.mybatis.handlers.graph.strategy;

import java.lang.reflect.Field;

public interface AggregateStrategy {
	
	void create(Object parent, Object entities, String fieldName);

	void update(Object parent, Object entities, String fieldName);
	
	void delete(Object parent, Object entities, String fieldName);
	
	boolean canSupport(Field field);
	
}
