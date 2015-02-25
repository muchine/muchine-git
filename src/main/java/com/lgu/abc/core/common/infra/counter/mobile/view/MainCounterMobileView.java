package com.lgu.abc.core.common.infra.counter.mobile.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounter;
import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounterRegistry;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.mobile.MobileItemView;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ROW")
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class MainCounterMobileView extends MobileItemView {
	
	private static final String APPROVAL_COUNTER_ID = "apr";
	private static final String CALENDAR_COUNTER_ID = "sch";

	@XStreamAlias("A01")
	private int approvalCount;
	
	@XStreamAlias("B02")
	private int calendarCount;
	
	@XStreamAlias("D01")
	private int boardCount = 0;
	
	public MainCounterMobileView(User user) {
		NotificationCounter approvalCounter = NotificationCounterRegistry.find(APPROVAL_COUNTER_ID, user.getCompany());
		approvalCount = approvalCounter == null ? 0 : approvalCounter.count(user);
		
		NotificationCounter calendarCounter = NotificationCounterRegistry.find(CALENDAR_COUNTER_ID, user.getCompany());
		calendarCount = calendarCounter == null ? 0 : calendarCounter.count(user);		
	}
	
}
