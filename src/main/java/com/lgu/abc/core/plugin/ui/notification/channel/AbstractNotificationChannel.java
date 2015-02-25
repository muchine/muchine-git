package com.lgu.abc.core.plugin.ui.notification.channel;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

public abstract class AbstractNotificationChannel extends AbstractModulePluggable implements NotificationChannel {

	protected AbstractNotificationChannel(NotificationChannelRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}
	
}
