package com.lgu.abc.core.transport.mail;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.transport.mail.MailMessage;
import com.lgu.abc.core.transport.mail.mailer.SpringMailer;
import com.lgu.abc.test.common.base.BaseTest;

public class SpringMailerTest extends BaseTest {

	@Autowired
	private SpringMailer mailer;
	
	@Test
	public void testSend() throws IOException {
		String from = "systemadmin@abc.onnet21.com";
		String to = "sejoonlim@lguplus.co.kr";
		String subject = "test mail";
		String content = createContent();
		MailMessage message = new MailMessage(from, to, subject, content);
		
		mailer.send(message);
	}
	
	@Test
	public void testSendWithUser() throws IOException {
		String from = "systemadmin@abc.onnet21.com";
		String to = "sejoonlim@lguplus.co.kr";
		String subject = "test mail";
		String content = createContent();
		MailMessage message = new MailMessage(from, to, subject, content);
		
		mailer.send(message, getSession());
	}
	
	private String createContent() throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(getClass().getResourceAsStream("notification-template.html"), writer, "UTF-8");
		
		return writer.toString();
	}
	
	public static void main(String[] args) throws IOException {
		SpringMailerTest test = new SpringMailerTest();
		System.out.println(test.createContent());
	}
	
}
