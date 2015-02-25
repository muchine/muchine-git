package com.lgu.abc.core.plugin.proto.user.init;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;

@Component
public class GlobalUserFinalizer {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private Set<UserFinalizable> terminatables = new HashSet<UserFinalizable>();

	public synchronized void add(UserFinalizable terminatable) {
		terminatables.add(terminatable);
	}
	
	public void terminate(User user) {
		logger.info("terminate user... user: " + user.getId());
		for (UserFinalizable terminatable : terminatables) {
			terminatable.finalize(user);
		}
	}
	
	public void reset(User user) {
		logger.info("reset user... user: " + user.getId());
		for (UserFinalizable terminatable : terminatables) {
			terminatable.reset(user);
		}
	}

}
