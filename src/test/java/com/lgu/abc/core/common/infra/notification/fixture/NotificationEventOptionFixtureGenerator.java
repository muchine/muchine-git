package com.lgu.abc.core.common.infra.notification.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.service.NotificationEventOptionService;
import com.lgu.abc.test.support.fixture.DefaultFixtureGenerator;

@Component
public class NotificationEventOptionFixtureGenerator extends DefaultFixtureGenerator<NotificationEventOption, NotificationEventOption> {

	@Autowired
	protected NotificationEventOptionFixtureGenerator(NotificationEventOptionFixtureFactory factory, NotificationEventOptionService service) {
		super(factory, service);
	}

}
