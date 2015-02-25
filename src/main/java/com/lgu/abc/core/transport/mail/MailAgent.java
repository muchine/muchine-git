package com.lgu.abc.core.transport.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.AbcConfig;

@Component
public class MailAgent {

	private final Mailer mailer;
	
	private final String from;
	
	private final String[] tos;
	
	@Autowired
	MailAgent(Mailer mailer, AbcConfig configuration) {
		this(mailer, configuration.formMailSend().address(), configuration.system().contact().emails());
	}
	
	MailAgent(Mailer mailer, String from, String[] tos) {
		this.mailer = mailer;
		this.from = from;
		this.tos = tos;
	}
	
	public void send(String subject, String content) {
		for (String to : tos) {
			MailMessage message = new MailMessage(from, to, subject, content);
			mailer.send(message);
		}		
	}
	
}
