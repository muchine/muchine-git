package com.lgu.abc.core.common.infra.error;

import java.util.Date;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Field;

public @Data class Error {

	private String system;
	
	private String domain;
	
	@Field("client")
	private String clientIp;
	
	@Field("user")
	private String userId;
	
	private String message;
		
	private Date registered = new Date();
	
	public Error() {}
	
	public Error(String system, String message, String domain, String clientIp, String userId) {
		this.system = system;
		this.message = message;
		this.domain = domain;
		this.clientIp = clientIp;
		this.userId = userId;
	}
	
}
