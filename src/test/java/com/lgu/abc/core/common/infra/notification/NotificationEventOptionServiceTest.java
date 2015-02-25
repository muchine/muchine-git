package com.lgu.abc.core.common.infra.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.common.infra.notification.domain.NotificationChannelSet;
import com.lgu.abc.core.common.infra.notification.domain.NotificationEvent;
import com.lgu.abc.core.common.infra.notification.fixture.NotificationEventOptionFixtureFactory;
import com.lgu.abc.core.common.infra.notification.service.NotificationEventOptionService;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.test.common.base.service.DomainServiceTest;
import com.u2ware.springfield.service.EntityService;

@Operations(type = {
	OperationType.CREATE,
	OperationType.UPDATE,
	OperationType.DELETE,
	OperationType.FIND_FORM,
	OperationType.MULTI_UPDATE
})
public class NotificationEventOptionServiceTest extends DomainServiceTest<NotificationEventOption, NotificationEventOption> {

	@Autowired
	private NotificationEventOptionService service;
	
	@Autowired
	private NotificationEventOptionFixtureFactory factory;
	
	@Override
	protected EntityService<NotificationEventOption, NotificationEventOption> initService() {
		this.setFixtureFactory(factory);
		return service;
	}

	@Override
	protected List<NotificationEventOption> getCreatedList() {
		return null;
	}

	@Override
	protected List<NotificationEventOption> getUpdatedList() {
		List<NotificationEventOption> options = new ArrayList<NotificationEventOption>();
		
		options.add(factory.getUpdated());
		
		NotificationEvent event = new NotificationEvent("SCH_02");
		NotificationChannelSet channel = new NotificationChannelSet();
		
		channel.setMail(false);
		channel.setSms(true);
		channel.setNote(false);
		channel.setMessenger(true);
		channel.setWeb(false);
		
		options.add(new NotificationEventOption(event, channel));
		
		return options;
	}

}
