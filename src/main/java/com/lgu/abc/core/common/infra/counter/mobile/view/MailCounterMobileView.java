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
public @Data class MailCounterMobileView extends MobileItemView {

	private static final String MAIL_COUNTER_ID = "mail";
	
	@XStreamAlias("MSG_COUNT")
	private final int count;
	
	public MailCounterMobileView(User user) {
		NotificationCounter counter = NotificationCounterRegistry.find(MAIL_COUNTER_ID, user.getCompany());
		this.count = counter.count(user);
	}
	
}
