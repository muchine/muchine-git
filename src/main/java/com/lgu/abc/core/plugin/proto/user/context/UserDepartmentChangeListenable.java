package com.lgu.abc.core.plugin.proto.user.context;

import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.prototype.org.user.User;

public interface UserDepartmentChangeListenable {

	void changing(User oldUser, User newUser) throws CoreException;
	
	void changed(User oldUser, User newUser);
	
}
