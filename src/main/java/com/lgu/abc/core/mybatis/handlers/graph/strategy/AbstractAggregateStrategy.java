package com.lgu.abc.core.mybatis.handlers.graph.strategy;

import java.lang.reflect.Field;

import lombok.Getter;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.infra.log.Logger;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

public abstract class AbstractAggregateStrategy implements AggregateStrategy {
	
	protected Logger logger = new Logger(this.getClass());
	
	@Getter
	private final DataHandler handler;
	
	protected AbstractAggregateStrategy(DataHandler handler) {
		Validate.notNull(handler, "data handler is null.");
		this.handler = handler;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public void create(Object parent, Object entities, String fieldName) {
		if (entities == null) return;
		
		Validate.notNull(parent, "parent is null.");
		Validate.notNull(fieldName, "field name is null.");
		
		for (Object entity : (Iterable<Object>) entities)
			handler.insert(parent, entity, fieldName);
	}

	public abstract void update(Object parent, Object entities, String fieldName);
	
	@Override
	public void delete(Object parent, Object entities, String fieldName) {
		Validate.notNull(parent, "parent is null.");
		Validate.notNull(fieldName, "field name is null.");
				
		handler.deleteAll(parent, fieldName);
	}
		
	public final boolean canSupport(Field field) {
		Aggregate annotation = field.getAnnotation(Aggregate.class);
		return annotation != null && canSupport(annotation);		
	}
	
	protected abstract boolean canSupport(Aggregate annotation);
	
}
