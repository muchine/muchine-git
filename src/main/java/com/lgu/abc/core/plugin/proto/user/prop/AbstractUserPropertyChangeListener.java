package com.lgu.abc.core.plugin.proto.user.prop;

import com.lgu.abc.core.prototype.org.user.User;

/**
 * Provides empty implementation of UserPropertyChangeListenable interface, which leads to be flexible to interface changes.
 * All classes outside core package is recommended to inherit this abstract class.
 * @author Sejoon
 *
 */
public abstract class AbstractUserPropertyChangeListener implements UserPropertyChangeListenable {

	protected AbstractUserPropertyChangeListener(GlobalUserPropertyChangeListener listener) {
		listener.add(this);
	}
	
	@Override
	public void loginIdChanged(User oldUser, User newUser) {
		
	}

	@Override
	public void nameChanged(User user) {
		
	}
	
}
