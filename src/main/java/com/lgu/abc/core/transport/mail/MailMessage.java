package com.lgu.abc.core.transport.mail;

import lombok.Data;

public @Data class MailMessage {

	private String from;
	
	private String to;
	
	private String subject;
	
	private String message;
	
	private boolean html = true;
	
	public MailMessage() {}
	
	public MailMessage(String from, String to, String subject, String message) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.message = message;
	}
	
}
