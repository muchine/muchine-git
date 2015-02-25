package com.lgu.abc.core.web.session.cache;

import java.util.Map;

import com.lgu.abc.core.prototype.org.user.User;

public interface CacheableSessionInitializable {

	Map<String, String> initialize(User user);
	
}
