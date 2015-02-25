package com.lgu.abc.core.prototype.org.user.query;

import lombok.Data;

public @Data class UserStatus {

	private boolean deleted = false;
	
	public UserStatus() {}
	
	public UserStatus(boolean deleted) {
		this.deleted = deleted;
	}
	
}
