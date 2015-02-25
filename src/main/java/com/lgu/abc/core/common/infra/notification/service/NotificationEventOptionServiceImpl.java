package com.lgu.abc.core.common.infra.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.repo.NotificationEventOptionRepository;

@Service
public class NotificationEventOptionServiceImpl extends DomainServiceImpl<NotificationEventOption, NotificationEventOption> 
		implements NotificationEventOptionService {

	@Autowired
	public NotificationEventOptionServiceImpl(NotificationEventOptionRepository repository) {
		super(repository);
	}

}
