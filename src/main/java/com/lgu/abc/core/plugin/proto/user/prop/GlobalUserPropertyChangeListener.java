package com.lgu.abc.core.plugin.proto.user.prop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;

/**
 * Global implementation that contains all local implementation class of each module. The local implementation in each module should 
 * take the reference of this class via Spring bean injection and add itself in order to get user property change event notification. 
 * @author Sejoon Lim
 * @since 2014. 3. 7.
 *
 */
@Component
public final class GlobalUserPropertyChangeListener implements UserPropertyChangeListenable {

	private final List<UserPropertyChangeListenable> listenables = new ArrayList<UserPropertyChangeListenable>();
	
	/**
	 * Register local implementation to this class.
	 * @param listenable the local implementation of user property change listener
	 */
	public synchronized void add(UserPropertyChangeListenable listenable) {
		listenables.add(listenable);
	}
	
	@Override
	public void loginIdChanged(User oldUser, User newUser) {
		for (UserPropertyChangeListenable listenable : listenables) {
			listenable.loginIdChanged(oldUser, newUser);
		}
	}
	
	@Override
	public void nameChanged(User user) {
		for (UserPropertyChangeListenable listenable : listenables) {
			listenable.nameChanged(user);
		}
	}

}
