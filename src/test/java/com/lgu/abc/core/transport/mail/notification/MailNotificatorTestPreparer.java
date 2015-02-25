package com.lgu.abc.core.transport.mail.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.transport.mail.Mailer;
import com.lgu.abc.core.transport.mail.MockMailer;
import com.lgu.abc.core.transport.mail.notification.MailNotificator;

@Component
public class MailNotificatorTestPreparer {

	@Autowired
	private Mailer mailer;
	
	@Autowired
	private MailNotificator notificator;
	
	public void setup(MockMailer mailer) {
		notificator.setMailer(mailer);
	}
	
	public void teardown() {
		notificator.setMailer(this.mailer);
	}
	
}
