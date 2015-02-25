package com.lgu.abc.core.common.domain.label;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.query.ResolvableQuery;
import com.lgu.abc.core.common.code.LabelType;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public abstract @Data class LabelQuery extends ResolvableQuery {
	
	private LabelType type;

	@Override
	public void clear() {
		super.clear();
		
		type = null;
	}
	
}
