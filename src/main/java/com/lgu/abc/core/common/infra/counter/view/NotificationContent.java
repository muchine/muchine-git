package com.lgu.abc.core.common.infra.counter.view;

import com.lgu.abc.core.prototype.org.user.User;

public interface NotificationContent {

	User user();
	
	String title();
	
	String elapsed();
	
	String uri();
		
}
