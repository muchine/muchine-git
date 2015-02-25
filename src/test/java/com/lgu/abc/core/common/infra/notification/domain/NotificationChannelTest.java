package com.lgu.abc.core.common.infra.notification.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lgu.abc.core.common.infra.notification.code.NotificationChannelCode;
import com.lgu.abc.core.common.infra.notification.domain.NotificationChannelSet;

public class NotificationChannelTest {

	private NotificationChannelSet channel = new NotificationChannelSet();
	
	@Before
	public void setup() {
		channel.setMail(true);
		channel.setSms(false);
		channel.setNote(true);
		channel.setMessenger(false);
		channel.setWeb(true);
	}
	
	@Test
	public void testToList() {
		List<String> list = channel.toList();
		
		assertThat(list.size(), is(3));
		assertThat(list.get(0), is(NotificationChannelCode.MAIL));
		assertThat(list.get(1), is(NotificationChannelCode.NOTE));
		assertThat(list.get(2), is(NotificationChannelCode.WEB));
	}
	
	@Test
	public void testToArray() {
//		String[] array = channel.toArray();
//		
//		assertThat(array.length, is(3));
//		assertThat(array[0], is(NotificationChannelCode.MAIL));
//		assertThat(array[1], is(NotificationChannelCode.NOTE));
//		assertThat(array[2], is(NotificationChannelCode.WEB));
	}
	
}
