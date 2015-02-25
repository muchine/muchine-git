package com.lgu.abc.core.common.infra.notification.fixture;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.domain.NotificationChannelSet;
import com.lgu.abc.core.common.infra.notification.domain.NotificationEvent;
import com.lgu.abc.test.support.QueryTester;
import com.lgu.abc.test.support.fixture.AbstractFixtureFactory;

@Component
public class NotificationEventOptionFixtureFactory extends AbstractFixtureFactory<NotificationEventOption, NotificationEventOption> {

	@Override
	public NotificationEventOption getCreated() {
		NotificationEvent event = new NotificationEvent("TEST_01");
		event.setDescription("This is creation test.");
		
		NotificationChannelSet channel = new NotificationChannelSet();
		channel.setMail(false);
		channel.setSms(true);
		channel.setNote(false);
		channel.setMessenger(true);
		channel.setWeb(false);
		
		return new NotificationEventOption(event, channel);
	}

	@Override
	public NotificationEventOption getUpdated() {
		NotificationEvent event = new NotificationEvent("SCH_01");
		NotificationChannelSet channel = new NotificationChannelSet();
		
		channel.setMail(true);
		channel.setSms(false);
		channel.setNote(true);
		channel.setMessenger(false);
		channel.setWeb(true);
		
		return new NotificationEventOption(event, channel);
	}

	@Override
	public List<QueryTester<NotificationEventOption>> getQuery() {
		List<QueryTester<NotificationEventOption>> testers = new ArrayList<QueryTester<NotificationEventOption>>();
		
		QueryTester<NotificationEventOption> tester = new QueryTester<NotificationEventOption>(new NotificationEventOption(), true);		
		testers.add(tester);
		
		return testers;
	}

}
