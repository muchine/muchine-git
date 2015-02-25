package com.lgu.abc.core.common.notification;

import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.prototype.org.user.User;

public interface NotificationReceiverExtractor {

	Iterable<User> extract(User actor, Protectable entity);
	
}
