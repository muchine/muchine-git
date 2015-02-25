package com.lgu.abc.core.mybatis.handlers.graph.strategy.file;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.ActionEntity;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class FileEntityMap extends ActionEntity {

	private final String fileId;
	
	private final String entityId;
		
	public FileEntityMap(BaseEntity entity, String fileId) {
		this.fileId = fileId;
		this.entityId = entity.getId();
		
		setActor(entity.getActor());
	}
	
}
