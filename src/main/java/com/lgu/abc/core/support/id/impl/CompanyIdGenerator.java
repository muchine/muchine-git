package com.lgu.abc.core.support.id.impl;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.support.id.ChainableIdGenerator;
import com.lgu.abc.core.support.id.annotation.CompanyId;

public class CompanyIdGenerator extends ChainableIdGenerator {

	@Override
	protected String generate(Object entity) {
		return ((BaseEntity) entity).getActor().getCompany().getId();
	}

	@Override
	public boolean canSupport(Object entity) {
		Validate.notNull(entity, "entity is null.");
		logger.trace("can support: " + (entity.getClass().isAnnotationPresent(CompanyId.class) && entity instanceof BaseEntity));
		return entity.getClass().isAnnotationPresent(CompanyId.class) && entity instanceof BaseEntity;
	}

}
