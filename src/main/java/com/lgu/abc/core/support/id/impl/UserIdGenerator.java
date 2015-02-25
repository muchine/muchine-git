package com.lgu.abc.core.support.id.impl;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.support.id.ChainableIdGenerator;
import com.lgu.abc.core.support.id.annotation.UserId;

public class UserIdGenerator extends ChainableIdGenerator {

	@Override
	protected String generate(Object entity) {
		return ((BaseEntity) entity).getActor().getId();
	}

	@Override
	public boolean canSupport(Object entity) {
		Validate.notNull(entity, "entity is null.");
		logger.trace("annotation: " + entity.getClass().isAnnotationPresent(UserId.class));
		logger.trace("instance: " + (entity instanceof BaseEntity));
		logger.trace("can support: " + (entity.getClass().isAnnotationPresent(UserId.class) && entity instanceof BaseEntity));
		return entity.getClass().isAnnotationPresent(UserId.class) && entity instanceof BaseEntity;
	}

}
