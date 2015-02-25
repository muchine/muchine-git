package com.lgu.abc.core.common.infra.counter.mobile.command;

import lombok.Data;

public @Data class MailCounterMobileCommand {

	private String userid;
	
	private String domain;
	
	public void setUSERID(String userId) {
		this.userid = userId;
	}
	
	public void setDOMAIN(String domain) {
		this.domain = domain;
	}
	
}
