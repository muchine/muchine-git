package com.lgu.abc.core.plugin.ui.notification.manager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;
import com.lgu.abc.core.common.notification.NotificationAgent;
import com.lgu.abc.core.common.notification.domain.NotificationBean;

public abstract class AbstractNotificationManager extends AbstractModulePluggable implements NotificationManager {

	@Autowired
	private NotificationAgent agent;
	
	protected AbstractNotificationManager(NotificationManagerRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}
	
	@Override
	public final void synchronize(String userId) {
		if (StringUtils.isEmpty(userId)) return;
		
		NotificationBean bean = new NotificationBean();
		bean.setType(type());
		bean.addReceiver(userId);
		bean.setTargetDiv("99");
		
		agent.sendNotification(bean);
	}

	@Override
	public final int order() {
		return 0;
	}
	
	protected abstract String type();

}
