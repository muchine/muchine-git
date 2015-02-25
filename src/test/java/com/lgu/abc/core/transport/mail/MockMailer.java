package com.lgu.abc.core.transport.mail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lombok.Getter;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.transport.mail.MailMessage;
import com.lgu.abc.core.transport.mail.Mailer;
import com.lgu.abc.core.transport.mail.exception.MailSendingFailureException;

public class MockMailer implements Mailer {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Getter
	private final List<MailMessage> messages = new ArrayList<MailMessage>();
	
	@Override
	public void send(MailMessage message) throws MailSendingFailureException {
		logger.debug("send mail message... " + message);
		messages.add(message);
	}
	
	@Override
	public void send(MailMessage message, User sender) throws MailSendingFailureException {
		send(message);
	}

}
