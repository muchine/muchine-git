package com.lgu.abc.core.common.notification;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.notification.domain.NotificationBean;
import com.lgu.abc.core.common.notification.domain.NotificationResponse;

public class MockNotificationManager extends NotificationAgent {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private NotificationMonitorable monitorable;
	
	private NotificationAgent originator;
	
	@Getter
	private List<NotificationBean> notifications = new ArrayList<NotificationBean>();
	
	public MockNotificationManager() {}
	
	public MockNotificationManager(NotificationMonitorable monitorable) {
		inject(monitorable);
	}

	public void inject(NotificationMonitorable monitorable) {
		this.monitorable = monitorable;
		this.originator = monitorable.getNotificationManager();
		
		monitorable.setNotificationManager(this);
	}
	
	public void restore() {
		monitorable.setNotificationManager(this.originator);
		
		this.monitorable = null;
		this.originator = null;
	}
	
	@Override
	public NotificationResponse sendNotification(NotificationBean bean) {
		notifications.add(bean);
		logger.debug("notification sent: " + bean);
		
		return new NotificationResponse("200", "ok");
	}
	
	public boolean hasSentNotification(NotificationBean bean) {
		for (NotificationBean b : notifications) {
			if (b.equals(bean)) return true;
		}
		
		return false;
	}

}
