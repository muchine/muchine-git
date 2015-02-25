package com.lgu.abc.core.plugin.ui.notification.counter.view;

import java.util.Locale;

import lombok.Data;

import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounter;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class NotificationCounterView {

	private final NotificationCounter counter;
	
	private final User actor;
		
	public NotificationCounterView(NotificationCounter counter, User actor) {
		this.counter = counter;		
		this.actor = actor;
	}
	
	public String getId() {
		return counter.id();
	}
	
	public String getName() {
		return counter.name().getValue(locale());
	}
	
	public String getTitle() {
		return counter.title().getValue(locale());
	}
	
	public String getIcon() {
		return counter.icon();
	}
	
	public String getUri() {
		return counter.uri();
	}
	
	public int getCount() {
		return counter.count(actor);
	}
	
	private Locale locale() {
		return actor.getLocale();
	}
	
}
