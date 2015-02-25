package com.lgu.abc.core.mybatis.handlers.graph.strategy.label;

import java.lang.reflect.Field;

import com.lgu.abc.core.common.domain.label.annotation.Labels;
import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.base.IdentifiableMapperStrategy;

public class LabelStrategy extends IdentifiableMapperStrategy {
	
	public LabelStrategy(DataHandler handler) {
		super(handler);
	}

	@Override
	public boolean canSupport(Field field) {
		return field.isAnnotationPresent(Labels.class);
	}
	
}
