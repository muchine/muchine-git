package com.lgu.abc.core.prototype.org.user.repo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;

@Component
public class UserRegistry {
	
	protected static final Log logger = LogFactory.getLog(UserRegistry.class);
	
	private static UserRepository repository;
	
	@Autowired
	public UserRegistry(UserRepository repo) {
		Validate.isTrue(repository == null, "repository should be instantiated only once by Spring.");
		repository = repo;
	}
	
	public static User get(String userId) {
		if (StringUtils.isEmpty(userId)) return null;
		
		logger.trace("get user: " + userId);
		return repository == null ? null : repository.read(userId);
	}
	
	public static void evict(String userId) {
		logger.debug("evict user: " + userId);
		repository.evict(userId);
	}
		
}
