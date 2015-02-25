package com.lgu.abc.core.mybatis.handlers.graph.strategy.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.ActionEntity;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class EntityMap extends ActionEntity {

	private final String entityId;
	
	private final String mappedId;
	
	public EntityMap(BaseEntity entity, String mappedId) {
		this.entityId = entity.getId();
		this.mappedId = mappedId;
				
		setActor(entity.getActor());
	}
	
}
