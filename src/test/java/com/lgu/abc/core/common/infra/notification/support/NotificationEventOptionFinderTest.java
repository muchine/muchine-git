package com.lgu.abc.core.common.infra.notification.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.fixture.NotificationEventOptionFixtureGenerator;
import com.lgu.abc.test.common.base.BaseTest;

public class NotificationEventOptionFinderTest extends BaseTest {

	@Autowired
	private NotificationEventOptionFinder finder;
	
	@Autowired
	private NotificationEventOptionFixtureGenerator generator;
	
	@Test
	public void testFindByUser() {
		// Given
		NotificationEventOption option = generator.generate();
		
		// When
		Iterable<NotificationEventOption> found = finder.findByUser(getSession());
		
		// Then
		assertThat(contains(option, found), is(true));
	}
	
	private boolean contains(NotificationEventOption generated, Iterable<NotificationEventOption> found) {
		for (NotificationEventOption option : found) {
			if (option.getEvent().equals(generated.getEvent())) return true;
		}
		
		return false;
	}
	
}
