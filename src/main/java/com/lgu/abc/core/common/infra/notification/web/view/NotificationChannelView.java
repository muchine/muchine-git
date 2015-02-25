package com.lgu.abc.core.common.infra.notification.web.view;

import lombok.Data;

public @Data class NotificationChannelView {

	private final String code;
	
	private final String name;
	
	private final boolean enabled;
	
	NotificationChannelView(String code, String name, boolean enabled) {
		this.code = code;
		this.name = name;
		this.enabled = enabled;
	}
	
}
