package com.lgu.abc.core.mybatis.handlers.graph.strategy.container;

import java.lang.reflect.Field;

import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.base.IdentifiableMapperStrategy;
import com.lgu.abc.core.support.annotation.populator.Container;

public class ContainerStrategy extends IdentifiableMapperStrategy {

	public ContainerStrategy(DataHandler handler) {
		super(handler);
	}

	@Override
	public boolean canSupport(Field field) {
		/*
		 * NOTE if entity has one-to-one relationship with container, do nothing here.
		 * Entity SQL mapper will handle the association.
		 */
		Container annotation = field.getAnnotation(Container.class);
		return annotation != null && annotation.collection();
	}

}
