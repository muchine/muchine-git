package com.lgu.abc.core.transport.sms;

import java.util.Date;

import lombok.Data;

public @Data class SmsMessage {

	private final String sender = "15442164";
	
	private final String receiver;
	
	private final String content;
	
	private final Date sentDate = new Date();
	
	public SmsMessage(String receiver, String content) {
		this.receiver = receiver;
		this.content = content;
	}
	
}
