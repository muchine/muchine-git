package com.lgu.abc.core.transport.mail.mailer;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.transport.mail.MailMessage;
import com.lgu.abc.core.transport.mail.Mailer;
import com.lgu.abc.core.transport.mail.domain.CustomMimeMessage;
import com.lgu.abc.core.transport.mail.exception.MailSendingFailureException;

@Component
public class SpringMailer implements Mailer {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private JavaMailSender sender;
	
	/*
	 * This is for unit test. Do not set enabled to false in production environment 
	 */
	@Setter
	private boolean enabled = true;
	
	@Override
	public void send(MailMessage message) throws MailSendingFailureException {
		MimeMessage mimeMessage = createMimeMessage(message);
		sendMimeMessage(mimeMessage);
	}
	
	@Override
	public void send(MailMessage message, User sender) throws MailSendingFailureException {
		MimeMessage mimeMessage = createCustomMimeMessage(message, sender);		
		sendMimeMessage(mimeMessage);
	}
	
	private MimeMessage createMimeMessage(MailMessage message) {
		logger.debug("send message: " + message);
		
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		
		try {
			helper.setFrom(message.getFrom());
			helper.setTo(message.getTo());
			helper.setSubject(message.getSubject());
			helper.setText(message.getMessage(), message.isHtml());
		} catch(MessagingException e) {
			throw new MailSendingFailureException(e);
		}
		
		return mimeMessage;
	}
	
	private MimeMessage createCustomMimeMessage(MailMessage message, User sender) {
		MimeMessage mimeMessage = createMimeMessage(message);
		
		try {
			return new CustomMimeMessage(mimeMessage, sender.getId());
		} catch(MessagingException e) {
			throw new MailSendingFailureException(e);
		}
	}
	
	private void sendMimeMessage(MimeMessage message) {
		try {
			if (!enabled) return;
			sender.send(message);
		} catch(MailException e) {
			logError(e);
		}
	}
	
	private void logError(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		
		logger.error(writer.toString());
	}

}
