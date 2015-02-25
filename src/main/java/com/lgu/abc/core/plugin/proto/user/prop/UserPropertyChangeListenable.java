package com.lgu.abc.core.plugin.proto.user.prop;

import com.lgu.abc.core.prototype.org.user.User;

/**
 * Represents a listener that can receive notification when user property is changed.
 * @author Sejoon Lim
 * @since 2014. 3. 7.
 *
 */
public interface UserPropertyChangeListenable {

	void loginIdChanged(User oldUser, User newUser);
	
	void nameChanged(User user);
	
}
