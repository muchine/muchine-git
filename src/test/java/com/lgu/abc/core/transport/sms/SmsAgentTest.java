package com.lgu.abc.core.transport.sms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.test.common.base.BaseTest;

public class SmsAgentTest extends BaseTest {

	@Autowired
	private SmsAgent agent;
	
	@Test
	public void testSend() {
		agent.send("01080802361", "This is message.");
	}
	
	@Test
	public void testSendAll() {
		agent.send("This is critical message.");
	}
	
}
