package com.lgu.abc.core.web.interceptor.security.ip;

import com.lgu.abc.core.prototype.org.user.User;

public interface IpFilterPolicy {

	boolean allows(String ip, User user);
	
}
