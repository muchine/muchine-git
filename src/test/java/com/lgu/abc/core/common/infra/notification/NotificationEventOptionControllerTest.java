package com.lgu.abc.core.common.infra.notification;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.lgu.abc.core.base.controller.BaseController;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;
import com.lgu.abc.core.common.infra.notification.web.NotificationEventOptionController;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.annotation.web.View;
import com.lgu.abc.test.common.base.controller.MacroCommandControllerTest;
import com.lgu.abc.test.common.base.factory.PageableDisabledParameterFactory;
import com.lgu.abc.test.common.base.operation.OperationTester;
import com.lgu.abc.test.common.base.operation.TestRequest;
import com.lgu.abc.test.support.TestUtils;
import com.u2ware.springfield.controller.EntityHandler;

@View("json") 
@Domain(NotificationEventOption.class)
public class NotificationEventOptionControllerTest extends MacroCommandControllerTest<NotificationEventOption, NotificationEventOption> {

	@Autowired
	private NotificationEventOptionController controller;
	
	@Override
	protected BaseController<NotificationEventOption, NotificationEventOption> initController() {
		return controller;
	}
	
	@Override
	public Map<String, String> getCreationParameters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("event.name", "SCH_01");
		params.put("channel.mail", "false");
		params.put("channel.sms", "true");
		params.put("channel.note", "false");
		params.put("channel.messenger", "true");
		params.put("channel.web", "false");
		
		return params;
	}

	@Override
	public Map<String, String> getUpdateFormParameters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("event.name", "SCH_01");
		
		return params;
	}

	@Override
	public Map<String, String> getUpdateParameters() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("event.name", "SCH_02");
		params.put("channel.mail", "true");
		params.put("channel.sms", "false");
		params.put("channel.note", "true");
		params.put("channel.messenger", "false");
		params.put("channel.web", "true");
		
		return params;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		return new PageableDisabledParameterFactory().getQueryParameters();
	}
	
	@Test
	public void testFindAll() throws Exception {
		// Given
		TestRequest request = requestFactory.findForm();
		
		// When
		OperationTester tester = getTesterFactory().create(request, "TEST FIND ALL", getQueryParameters(), EntityHandler.MODEL_QUERY_RESULT);
		ResultActions actions = tester.doTest();
		
		// Then
		Iterable<NotificationEventOption> found = TestUtils.getQueryResult(actions, NotificationEventOption.class);
		int count = 0;
		for (NotificationEventOption option : found) {
			assertThat(option.getChannel(), is(notNullValue()));
			count++;
		}
		
		assertTrue(count > 5);
	}

}
