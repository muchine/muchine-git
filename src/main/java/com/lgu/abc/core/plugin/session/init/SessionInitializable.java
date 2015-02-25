package com.lgu.abc.core.plugin.session.init;

import com.lgu.abc.core.prototype.org.user.User;

public interface SessionInitializable {

	SessionAttribute initialize(User user);
	
	String moduleId();
	
}
