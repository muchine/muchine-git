package com.lgu.abc.core.common.infra.notification;

import javax.validation.Valid;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.notification.domain.NotificationChannelSet;
import com.lgu.abc.core.common.infra.notification.domain.NotificationEvent;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class NotificationEventOption extends RootEntity {

	@Valid
	private NotificationEvent event;
	
	@Valid
	private NotificationChannelSet channel;
	
	public NotificationEventOption() {}
	
	public NotificationEventOption(NotificationEvent event, NotificationChannelSet channel) {
		this.event = event;
		this.channel = channel;
	}
	
}
