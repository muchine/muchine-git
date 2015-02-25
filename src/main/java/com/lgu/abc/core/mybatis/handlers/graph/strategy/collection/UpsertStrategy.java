package com.lgu.abc.core.mybatis.handlers.graph.strategy.collection;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AbstractAggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

/**
 * While looping child object list, update if exists otherwise insert. THe biggest drawback is that it does not support deletion objects.
 * Therefore, this strategy should be used only in the case when there are no deletion on rows. A little less risky than delete and insert
 * strategy, but has the same problem when there are many child objects. 
 *  
 * @author sejoon
 *
 */
public class UpsertStrategy extends AbstractAggregateStrategy implements AggregateStrategy {
	
	public UpsertStrategy(DataHandler handler) {
		super(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object parent, Object entities, String fieldName) {
		Validate.notNull(parent, "parent is null.");
		if (entities == null) return;
				
		for (Object entity : (Iterable<Object>) entities)
			getHandler().upsert(parent, entity, fieldName);
	}
	
	@Override
	protected boolean canSupport(Aggregate annotation) {
		return Type.UPSERT.equals(annotation.value());
	}

}
