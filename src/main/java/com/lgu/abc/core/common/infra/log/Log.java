package com.lgu.abc.core.common.infra.log;

import java.util.Date;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Field;

public @Data class Log {

	@Field("user")
	private final String userId;
	
	@Field("class")
	private final String className;
	
	@Field("method")
	private final String methodName;
	
	private final String message;
	
	private final Date date = new Date();
	
	Log(String userId, String className, String methodName, String message) {
		this.userId = userId;
		this.className = className;
		this.methodName = methodName;
		this.message = message;
	}
	
}
