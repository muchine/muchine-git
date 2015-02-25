package com.lgu.abc.core.mybatis.handlers.graph;

import java.lang.reflect.Field;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategyFactory;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.annotation.mapper.NoAggregate;
import com.lgu.abc.core.support.annotation.mapper.OnAggregate;
import com.lgu.abc.core.support.field.processor.FieldProcessor;
import com.lgu.abc.core.support.field.processor.FieldProcessorTemplate;

/**
 * Default object graph handler. Provide three strategy by default. <br>
 * 1. Delete and insert strategy <br>
 * 2. Upsert strategy <br>
 * 3. Dirty check strategy.<br>
 * <br>
 * Strategy that will be applied to object graph is determined at runtime based on @Graph annotation.
 * 
 * @author sejoon
 *
 */
@Component
public class DefaultAggregateHandler implements AggregateHandler {
	
	protected Log logger = LogFactory.getLog(this.getClass());
	
	private final FieldProcessorTemplate processor = new FieldProcessorTemplate();
	
	@Autowired
	private AggregateStrategyFactory factory;
	
	@Override
	public void create(Object entity) {
		handle(entity, OperationType.CREATE);
	}

	@Override
	public void update(Object entity) {
		handle(entity, OperationType.UPDATE);
	}
	
	@Override
	public void delete(Object entity) {
		handle(entity, OperationType.DELETE);
	}
	
	private void handle(final Object entity, final OperationType op) {
		logger.debug("Aggregate handler operation: " + op);
		Validate.notNull(entity, "entity is null.");
		
		processor.process(entity, new FieldProcessor() {

			@Override
			public void process(Field field, Object object) throws Exception {
				if (canSkipFieldInspection(field)) return;
				
				Object property = field.get(object);
				if (canProcessFieldRecursively(field, object)) {
					logger.trace("process domain field... name: " + field.getName());
					processor.process(property, this);
				}
				
				logger.trace("processing field: " + field.getName());
				AggregateStrategy strategy = factory.create(field);
				if (strategy == null) return;
				
				switch (op) {
					case CREATE:
						strategy.create(entity, property, field.getName());
						break;
					case UPDATE:
						strategy.update(entity, property, field.getName());
						break;
					case DELETE:
						strategy.delete(entity, property, field.getName());
						break;
					default:
						throw new IllegalArgumentException("Unsupported Operation Type: " + op);
				}
			}
		});
	}
	
	private boolean canSkipFieldInspection(Field field) {
		if (field.isAnnotationPresent(OnAggregate.class)) return false;
		return field.isAnnotationPresent(NoAggregate.class) || field.getType().isAnnotationPresent(NoAggregate.class);
	}

	private boolean canProcessFieldRecursively(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
		return field.getType().isAnnotationPresent(Domain.class) && field.get(object) != null;
	}
	
}