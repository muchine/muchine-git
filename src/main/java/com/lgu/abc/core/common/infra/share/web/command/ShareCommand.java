package com.lgu.abc.core.common.infra.share.web.command;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.web.view.command.AbstractCommand;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public abstract @Data class ShareCommand<T extends ShareableEntity> extends AbstractCommand {

	private String id;
	
	private List<Accessor> accessors;
	
	@Override
	public RootEntity update(RootEntity retrieved) {
		ShareableEntity entity = (ShareableEntity) retrieved;
		entity.setAccessors(accessors);
		
		return entity;
	}

	@Override
	public RootEntity read() {
		T query = instantiate();
		query.setId(id);
		
		return query;
	}
		
	protected abstract T instantiate();
	
}
