package com.lgu.abc.core.base.domain.action;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class  DomainEntity<T> extends ActionEntity {
	
	private T parent;
	
}
