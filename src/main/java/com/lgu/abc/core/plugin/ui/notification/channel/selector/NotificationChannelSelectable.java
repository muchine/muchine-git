package com.lgu.abc.core.plugin.ui.notification.channel.selector;

import com.lgu.abc.core.plugin.ui.notification.channel.NotificationChannel;

public interface NotificationChannelSelectable {

	boolean canSelect(NotificationChannel channel);
	
}
