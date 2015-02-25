package com.lgu.abc.core.mybatis.handlers.graph.strategy.collection;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.utils.AggregateUtils;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AbstractAggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;


/**
 * Insertion and deletion is determined based on comparing new entities with old one. Provides best performance 
 * when there are a lot of child objects. Smart update that compares the contents of old and new objects and decides 
 * if the object is changed, is not yet supported.
 * 
 * @author sejoon
 * 
 */
public class DirtyCheckStrategy extends AbstractAggregateStrategy implements AggregateStrategy {

	public DirtyCheckStrategy(DataHandler handler) {
		super(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object parent, Object entities, String fieldName) {
		Validate.notNull(parent);
		if (entities == null) return;

		Iterable<Identifiable> oldEntities = (Iterable<Identifiable>) getHandler().findAll(parent, fieldName);
		Iterable<Identifiable> newEntities = (Iterable<Identifiable>) entities;
		
		List<Identifiable> removed = AggregateUtils.getRemovedEntities(oldEntities, newEntities);
		for (Identifiable entity : removed) {
			if (entity == null) continue;
			getHandler().delete(parent, entity, fieldName);
		}
		
		List<Identifiable> added = AggregateUtils.getAddedEntities(oldEntities, newEntities);
		for (Identifiable entity : added) {
			if (entity == null) continue;
			getHandler().insert(parent, entity, fieldName);
		}		
	}

	@Override
	protected boolean canSupport(Aggregate annotation) {
		return Type.DIRTY_CHECK.equals(annotation.value());
	}
	
}
