package com.lgu.abc.core.transport.mail;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.transport.mail.exception.MailSendingFailureException;

public interface Mailer {

	void send(MailMessage message) throws MailSendingFailureException;
	
	void send(MailMessage message, User sender) throws MailSendingFailureException;
	
}
