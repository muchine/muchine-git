package com.lgu.abc.core.prototype.org.user.fixture;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.service.UserService;
import com.lgu.abc.test.common.base.fixture.SessionFactory;

@Component
public class UserFixtureGenerator {
	
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private UserService service;
	
	public User generate() {
		// TODO replace this method to generate new user
		return generate(SessionFactory.ACTOR_ID);
	}
	
	public User generate(String userId) {
		Validate.notNull(userId);
		
		User query = new User();
		query.setId(userId);
		
		User found = this.service.read(query);
		
		if (found == null)
			throw new NullPointerException("User found result is null. user id: " + userId);
		
		logger.trace("user retrieved: " + found);
		return found;
	}

}
