package com.lgu.abc.core.common.code.vo;

import lombok.Data;

public @Data class ClassId {

	private final String id;
	
	public ClassId(String id) {
		this.id = id;
	}
	
}
