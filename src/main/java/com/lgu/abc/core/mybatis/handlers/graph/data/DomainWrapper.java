package com.lgu.abc.core.mybatis.handlers.graph.data;

import org.apache.commons.lang.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.common.interfaces.Identifiable;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class DomainWrapper extends ActionEntity {

	private final Object domain;
	
	private final Object parent;
	
	public DomainWrapper(Object domain, Object parent) {
		this.domain = domain;
		this.parent = parent;
	}

	@Override
	public String getId() {
		if (domain != null && domain instanceof Identifiable) {
			String id = ((Identifiable) domain).getId();
			if (!StringUtils.isEmpty(id)) return id;
		}
		
		return super.getId();
	}
	
}
