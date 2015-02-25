package com.lgu.abc.core.plugin.proto.user.context;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.plugin.proto.user.context.exception.UserDepartmentChangeException;
import com.lgu.abc.core.prototype.org.user.User;

public interface UserDepartmentChangeValidatable extends ModulePluggable {

	void validate(User user) throws UserDepartmentChangeException;
	
	boolean isRecoverable();
	
	void recover(User user);
	
}
