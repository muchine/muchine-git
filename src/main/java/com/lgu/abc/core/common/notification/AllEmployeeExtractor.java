package com.lgu.abc.core.common.notification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.support.UserFinder;

@Component
public class AllEmployeeExtractor implements NotificationReceiverExtractor {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserFinder finder;
	
	public Iterable<User> extract(User actor, Protectable entity) {
		Iterable<User> users = finder.findByCompanyId(actor.getCompany().getId());
		
		List<User> receivers = new ArrayList<User>();
		for (User user : users) {
			if (user.canRead(entity) && !user.identical(actor)) receivers.add(user);
		}
		
		return receivers;
	}
	
}
