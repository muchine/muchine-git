package com.lgu.abc.core.mybatis.handlers.graph.strategy.association;

import java.lang.reflect.Field;

import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.support.annotation.mapper.Association;

public class AssociationStrategy implements AggregateStrategy {

	private final DataHandler handler;
	
	public AssociationStrategy(DataHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void create(Object parent, Object entity, String fieldName) {
		if (entity == null) return;
		handler.insert(parent, entity, fieldName);
	}

	@Override
	public void update(Object parent, Object entity, String fieldName) {
		if (entity == null) {
			handler.deleteAll(parent, fieldName);
		} else {
			handler.upsert(parent, entity, fieldName);	
		}
	}

	@Override
	public void delete(Object parent, Object entity, String fieldName) {
		if (entity == null) return;
		handler.delete(parent, entity, fieldName);
	}

	@Override
	public boolean canSupport(Field field) {
		return field.getAnnotation(Association.class) != null;	
	}

}
