package com.lgu.abc.core.transport.mail.notification;

import lombok.Data;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.transport.mail.MailMessage;

public @Data class MailNotification {

	private final MailMessage message;
	
	private final User sender;
	
	public MailNotification(MailMessage message) {
		this(message, null);
	}
	
	public MailNotification(MailMessage message, User sender) {
		this.message = message;
		this.sender = sender;
	}
	
}
