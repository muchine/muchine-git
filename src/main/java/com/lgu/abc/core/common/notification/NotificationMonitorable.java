package com.lgu.abc.core.common.notification;

public interface NotificationMonitorable {

	void setNotificationManager(NotificationAgent notificationManager);
	
	NotificationAgent getNotificationManager();
	
}
