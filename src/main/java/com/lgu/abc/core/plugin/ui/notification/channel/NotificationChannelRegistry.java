package com.lgu.abc.core.plugin.ui.notification.channel;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;

@Component
public class NotificationChannelRegistry {

	private static final Set<NotificationChannel> channels = new HashSet<NotificationChannel>();
	
	public synchronized void add(NotificationChannel channel) {
		PluggableUtils.add(channel, channels);
	}
	
	public static Set<NotificationChannel> all() {
		return PluggableUtils.all(channels);
	}
		
}
