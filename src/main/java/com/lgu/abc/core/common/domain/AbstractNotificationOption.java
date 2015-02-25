package com.lgu.abc.core.common.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public abstract @Data class AbstractNotificationOption extends RootEntity {

	private boolean enabled;
	
	private List<NotificationEventOption> events;
	
	public void add(NotificationEventOption option) {
		if (events == null) events = new ArrayList<NotificationEventOption>();
		
		remove(option.getEvent().getName());
		events.add(option);
	}
		
	public NotificationEventOption find(String name) {
		if (events == null) return null;
		
		for (NotificationEventOption option : events) {
			if (name.equals(option.getEvent().getName())) return option;
		}
		
		return null;
	}
	
	public List<NotificationEventOption> findByPrefix(String prefix) {
		if (events == null) return null;
		
		List<NotificationEventOption> found = new ArrayList<NotificationEventOption>();
		for (NotificationEventOption option : events) {
			if (option.getEvent().getName().startsWith(prefix)) found.add(option);
		}
		
		return found;
	}
	
	public void remove(String name) {
		NotificationEventOption option = find(name);
		if (option != null) events.remove(option);
	}
	
}
