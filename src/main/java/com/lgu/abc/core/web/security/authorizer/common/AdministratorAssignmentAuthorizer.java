package com.lgu.abc.core.web.security.authorizer.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

public class AdministratorAssignmentAuthorizer {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	public boolean authorize(User actor, Iterable<User> users) {
		
		final Company company = actor.getCompany();
		
		for (User user : users) {
			if (!company.identical(user.getCompany())) {
				logger.warn("administrator is in different company: " + user.getCompany() + ", actor company: " + company);
				return false;
			}
		}
		
		return true;		
	}
	
}
