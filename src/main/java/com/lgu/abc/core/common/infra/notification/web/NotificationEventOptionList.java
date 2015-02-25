package com.lgu.abc.core.common.infra.notification.web;

import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.domain.Listable;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;

public @Data class NotificationEventOptionList implements Listable<NotificationEventOption> {

	private List<NotificationEventOption> entities;
	
	public NotificationEventOptionList() {}
	
	public NotificationEventOptionList(List<NotificationEventOption> entities) {
		this.entities = entities;
	}

}
