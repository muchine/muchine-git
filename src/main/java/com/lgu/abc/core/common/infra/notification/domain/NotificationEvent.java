package com.lgu.abc.core.common.infra.notification.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

public @Data class NotificationEvent {
	
	@NotNull @Length(min=0, max=10)
	private String name;
	
	@Length(min=0, max=50)
	private String description;
	
	public NotificationEvent() {}
	
	public NotificationEvent(String name) {
		this.name = name;
	}
	
}
