package com.lgu.abc.core.common.infra.notification.web;

import com.lgu.abc.core.base.view.AbstractMetamodel;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;

public class NotificationEventOptionMetamodel extends AbstractMetamodel<NotificationEventOption, NotificationEventOption> {

	public static final String BASE_URL = "/common/notification";
	
	NotificationEventOptionMetamodel() {
		super(NotificationEventOption.class, NotificationEventOption.class, BASE_URL);
	}

}
