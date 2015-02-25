package com.lgu.abc.core.transport.mail.notification;

import java.util.List;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.transport.mail.Mailer;

@Component
public class MailNotificator {

	@Autowired @Setter
	private Mailer mailer;
	
	public void send(List<MailNotification> notifications) {
		for (MailNotification notification : notifications) {
			send(notification);			
		}
	}
	
	public void send(MailNotification notification) {
		if (notification.getSender() != null) {
			mailer.send(notification.getMessage(), notification.getSender());	
		} else {
			mailer.send(notification.getMessage());
		}
	}
	
}
