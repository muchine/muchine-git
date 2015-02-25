package com.lgu.abc.core.plugin.session.init;

import lombok.Data;

public final @Data class SessionAttribute {

	private final String name;
	
	private final Object object;
	
	public SessionAttribute(String name, Object object) {
		this.name = name;
		this.object = object;
	}
	
}
