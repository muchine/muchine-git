package com.lgu.abc.core.mybatis.handlers.graph.strategy.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.DeleteInsertStrategy;

public abstract class IdentifiableMapperStrategy implements AggregateStrategy {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final AggregateStrategy strategy;
	
	protected IdentifiableMapperStrategy(DataHandler handler) {
		this.strategy = new DeleteInsertStrategy(handler);
	}
	
	protected IdentifiableMapperStrategy(AggregateStrategy strategy) {
		this.strategy = strategy;
	}
	
	@Override
	public final void create(Object parent, Object entities, String fieldName) {
		strategy.create(parent, maps(parent, entities), fieldName);
	}

	@Override
	public final void update(Object parent, Object entities, String fieldName) {
		strategy.update(parent, maps(parent, entities), fieldName);
	}

	@Override
	public final void delete(Object parent, Object entities, String fieldName) {
		strategy.delete(parent, maps(parent, entities), fieldName);
	}
	
	private List<EntityMap> maps(Object parent, Object entities) {
		if (entities == null) return null;
		
		Assert.isTrue(entities instanceof Iterable, "entities does not support iterable.");
		Assert.isTrue(parent instanceof BaseEntity, "parent object is not Identifiable type.");
		
		List<EntityMap> map = new ArrayList<EntityMap>();
		
		for (Object e : (Iterable<?>) entities) {
			Assert.isTrue(e instanceof Identifiable, "an element in entities is not Identifiable type.");
			map.add(new EntityMap((BaseEntity) parent, ((Identifiable) e).getId()));
		}
		
		return map;
	}
	
}
