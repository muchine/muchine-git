package com.lgu.abc.core.plugin.ui.notification.channel.selector;

import com.lgu.abc.core.plugin.ui.notification.channel.NotificationChannel;

public class AllNotificationChannelSelector implements NotificationChannelSelectable {

	private static final AllNotificationChannelSelector instance = new AllNotificationChannelSelector();
	
	private AllNotificationChannelSelector() {}
	
	@Override
	public boolean canSelect(NotificationChannel channel) {
		return true;
	}

	public static AllNotificationChannelSelector instance() {
		return instance;
	}
	
}
