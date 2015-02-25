package com.lgu.abc.core.common.infra.notification.web.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.plugin.SortableComparator;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.ModuleUtils;
import com.lgu.abc.core.plugin.ui.notification.channel.NotificationChannel;
import com.lgu.abc.core.plugin.ui.notification.channel.NotificationChannelRegistry;
import com.lgu.abc.core.plugin.ui.notification.channel.selector.AllNotificationChannelSelector;
import com.lgu.abc.core.plugin.ui.notification.channel.selector.NotificationChannelSelectable;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class NotificationChannelSetView {
	
	private List<NotificationChannelView> content = new ArrayList<NotificationChannelView>();
	
	private final User actor;
	
	private final NotificationChannelSelectable selectable;
	
	public NotificationChannelSetView(User actor) {
		this(actor, AllNotificationChannelSelector.instance());
	}
	
	public NotificationChannelSetView(User actor, NotificationChannelSelectable selectable) {
		this.actor = actor;
		this.selectable = selectable;
		
		addChannelViewContents();
	}
	
	private void addChannelViewContents() {
		List<NotificationChannel> channels = new ArrayList<NotificationChannel>(NotificationChannelRegistry.all());
		Collections.sort(channels, SortableComparator.instance());
		
		for (NotificationChannel channel : channels) {
			NotificationChannelView view = createView(channel);
			content.add(view);
		}
	}
	
	private NotificationChannelView createView(NotificationChannel channel) {
		String code = channel.id();
		String name = channel.name().getValue(actor.getLocale());
		boolean enabled = isEnabled(channel);
		
		return new NotificationChannelView(code, name, enabled);
	}
	
	private boolean isEnabled(NotificationChannel channel) {
		Module module = channel.module(actor.getCompany());
		return ModuleUtils.isEnabled(module) && selectable.canSelect(channel);
	}
	
}
