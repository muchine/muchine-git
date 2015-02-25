package com.lgu.abc.core.common.infra.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.infra.notification.domain.NotificationChannelSet;
import com.lgu.abc.core.common.infra.notification.domain.NotificationEvent;
import com.lgu.abc.core.common.infra.notification.service.NotificationEventOptionService;
import com.lgu.abc.core.plugin.proto.user.init.GlobalUserInitializer;
import com.lgu.abc.core.plugin.proto.user.init.UserInitializable;
import com.lgu.abc.core.prototype.org.user.User;

public abstract class AbstractNotificationInitializer implements UserInitializable {

	@Autowired
	private NotificationEventOptionService service;
	
	protected AbstractNotificationInitializer(GlobalUserInitializer initializer) {
		initializer.add(this);
	}
	
	@Override
	public final void initialize(User user) {
		for (String name : names()) {
			NotificationEvent event = new NotificationEvent(name);
			NotificationEventOption option = new NotificationEventOption(event, channels());
			option.prepareCreation(user);
			
			service.create(option);
		}
	}
	
	@Override
	public void update(User oldUser, User newUser) {
		
	}
	
	@Override
	public void finalize(User user) {
		for (String name : names()) {
			NotificationEvent event = new NotificationEvent(name);
			NotificationEventOption option = new NotificationEventOption(event, channels());
			option.setActor(user);
			
			service.delete(option);
		}
	}
	
	@Override
	public void reset(User user) {
		
	}

	protected abstract List<String> names();
	
	protected abstract NotificationChannelSet channels();

}
