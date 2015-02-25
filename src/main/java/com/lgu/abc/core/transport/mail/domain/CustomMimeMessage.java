package com.lgu.abc.core.transport.mail.domain;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomMimeMessage extends MimeMessage {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String messageID;

	public CustomMimeMessage(MimeMessage mimeMessage, String userId) throws MessagingException {
		super(mimeMessage);
		this.messageID = getUniqueMessageIDValue(userId);
		
		logger.debug("message id: " + messageID);
	}
	
	public CustomMimeMessage(Session session, String userId) {
		super(session);
		this.messageID = this.getUniqueMessageIDValue(userId);
	}

	private String getUniqueMessageIDValue(String userId) {
		StringBuilder s = new StringBuilder();

		s.append("<").append(s.hashCode()).append('.').append(System.currentTimeMillis())
				.append('.').append(userId).append("@ABCMAIL").append(">");
		return s.toString();
	}
	
	@Override
	public String getMessageID() throws MessagingException {
		return messageID;
	}

	@Override
	protected void updateMessageID() throws MessagingException {
		setHeader("Message-ID", messageID);
	}
}
