package com.lgu.abc.core.base.view;

import lombok.Data;

import com.lgu.abc.core.base.domain.action.ActionResult;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.interfaces.Identifiable;

public abstract @Data class AbstractJsonView implements Identifiable {

	private final String id;
	
	private final ActionResult result;
	
	protected AbstractJsonView(RootEntity entity) {
		this.id = entity.getId();
		this.result = entity.getResult();
	}

	@Override
	public boolean identical(Identifiable object) {
		if (!(object instanceof AbstractJsonView)) return false;
		return this.id.equals(object.getId());
	}
	
}
