package com.lgu.abc.core.common.infra.notification.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.service.NotificationEventOptionService;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class NotificationEventOptionFinder {

	@Autowired
	private NotificationEventOptionService service;
		
	public Iterable<NotificationEventOption> findByUser(User user) {
		NotificationEventOption query = new NotificationEventOption();
		query.setActor(user);
		
		return service.find(query, null);
	}
	
}
