package com.lgu.abc.core.mybatis.handlers.graph.strategy.collection;

import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AbstractAggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

public class NoUpdateStrategy extends AbstractAggregateStrategy implements AggregateStrategy {

	public NoUpdateStrategy(DataHandler handler) {
		super(handler);
	}

	@Override
	public void update(Object parent, Object entities, String fieldName) {
		/*
		 * This strategy does nothing for update. Support only creation and deletion
		 */
		logger.debug("No update strategy applied to the field: " + fieldName + ", entity: " + parent);
	}
	
	@Override
	protected boolean canSupport(Aggregate annotation) {
		return Type.NO_UPDATE.equals(annotation.value());
	}

}
