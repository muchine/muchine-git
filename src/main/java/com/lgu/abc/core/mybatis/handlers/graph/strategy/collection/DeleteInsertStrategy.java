package com.lgu.abc.core.mybatis.handlers.graph.strategy.collection;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AbstractAggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

/**
 * Delete all old child objects and insert newly. This is the simplest approach.
 * However it has a little risk because deleting physical row, also performace
 * will be degraded when there are many child objects
 * 
 * @author sejoon
 * 
 */
public class DeleteInsertStrategy extends AbstractAggregateStrategy implements AggregateStrategy {

	public DeleteInsertStrategy(DataHandler handler) {
		super(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object parent, Object entities, String fieldName) {
		Validate.notNull(parent, "parent is null.");

		getHandler().deleteAll(parent, fieldName);

		if (entities == null) return;
		
		for (Object entity : (Iterable<Object>) entities)
			getHandler().insert(parent, entity, fieldName);
	}
	
	@Override
	protected boolean canSupport(Aggregate annotation) {
		return Type.DELETE_INSERT.equals(annotation.value());
	}

}
