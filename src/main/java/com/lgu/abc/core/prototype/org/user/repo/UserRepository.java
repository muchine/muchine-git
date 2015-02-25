package com.lgu.abc.core.prototype.org.user.repo;

import com.lgu.abc.core.prototype.base.repo.PrototypeRepository;
import com.lgu.abc.core.prototype.org.user.User;

public interface UserRepository extends PrototypeRepository<User> {

	public static final String CACHE_NAME = "users";
	
}
