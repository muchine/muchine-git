package com.lgu.abc.core.plugin.proto.user.init;

import com.lgu.abc.core.prototype.org.user.User;

/**
 * Represents a listener that listens the user creation event and initializes the user environment of the module. For example, user
 * initialization can be done on user's default option setting for the module, default label creation, etc. It also handles user update and
 * termination events.
 * 
 * @author Sejoon Lim
 * @since 2014. 3. 10.
 * 
 */
public interface UserInitializable extends UserFinalizable {

	/**
	 * Initialize default user environment of the module for the given user.
	 * @param user the user who has been created by administrator
	 */
	void initialize(User user);
	
	/**
	 * Update when a user profile has been changed.
	 * @param oldUser a user profile before update
	 * @param newUser a user profile after update
	 */
	void update(User oldUser, User newUser);
	
}
