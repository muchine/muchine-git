package com.lgu.abc.core.common.infra.notification.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

import com.lgu.abc.core.common.infra.notification.code.NotificationChannelCode;
import com.lgu.abc.core.common.interfaces.Cleanable;

public @Data class NotificationChannelSet implements Cleanable {

	@NotNull
	private boolean mail;
	
	@NotNull
	private boolean sms;
	
	@NotNull
	private boolean note;
	
	@NotNull
	private boolean messenger;
	
	@NotNull
	private boolean web;
	
	@NotNull
	private boolean push;
	
	public NotificationChannelSet() {}
	
	public NotificationChannelSet(boolean mail, boolean sms, boolean note, boolean messenger, boolean web, boolean push) {
		this.mail = mail;
		this.sms = sms;
		this.note = note;
		this.messenger = messenger;
		this.web = web;
		this.push = push;
	}
	
	public NotificationChannelSet(List<String> codes) {
		setChannels(codes);
	}
	
	public List<String> toList() {
		List<String> list = new ArrayList<String>();
		
		if (mail) list.add(NotificationChannelCode.MAIL);
		if (sms) list.add(NotificationChannelCode.SMS);
		if (note) list.add(NotificationChannelCode.NOTE);
		if (messenger) list.add(NotificationChannelCode.MESSENGER);
		if (web) list.add(NotificationChannelCode.WEB);
		if (push) list.add(NotificationChannelCode.PUSH);
		
		return list;
	}
	
	public void setChannels(List<String> codes) {
		clean();
		if (codes == null) return;
		
		for (String code : codes) {
			NotificationChannelCode channel = new NotificationChannelCode(code);
			
			if (NotificationChannelCode.MAIL.equals(channel.getCode())) mail = true;
			else if (NotificationChannelCode.SMS.equals(channel.getCode())) sms = true;
			else if (NotificationChannelCode.NOTE.equals(channel.getCode())) note = true;
			else if (NotificationChannelCode.MESSENGER.equals(channel.getCode())) messenger = true;
			else if (NotificationChannelCode.WEB.equals(channel.getCode())) web = true;
			else if (NotificationChannelCode.PUSH.equals(channel.getCode())) push = true;
			else throw new IllegalArgumentException("invalid notification channel code: " + code);
		}
	}
	
	public List<String> getChannels() {
		return toList();
	}
	
	public void clean() {
		mail = false;
		sms = false;
		note = false;
		messenger = false;
		web = false;
		push = false;
	}
	
}
