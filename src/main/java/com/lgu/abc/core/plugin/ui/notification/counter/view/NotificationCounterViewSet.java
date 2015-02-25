package com.lgu.abc.core.plugin.ui.notification.counter.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounter;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class NotificationCounterViewSet {

	private final List<NotificationCounterView> content = new ArrayList<NotificationCounterView>();
	
	public NotificationCounterViewSet(Iterable<NotificationCounter> counters, User actor) {
		initialize(counters, actor);
	}
	
	private void initialize(Iterable<NotificationCounter> counters, User actor) {
		if (IterableUtils.isEmpty(counters)) return;

		for (NotificationCounter counter : counters) 
			content.add(new NotificationCounterView(counter, actor));
	}
	
}
