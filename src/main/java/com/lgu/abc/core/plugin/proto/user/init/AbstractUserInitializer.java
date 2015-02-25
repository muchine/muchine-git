package com.lgu.abc.core.plugin.proto.user.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.prototype.org.user.User;

public abstract class AbstractUserInitializer implements UserInitializable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void update(User oldUser, User newUser) {

	}

	@Override
	public void reset(User user) {
		
	}

}
