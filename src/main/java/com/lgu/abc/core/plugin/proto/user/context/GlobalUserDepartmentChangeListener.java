package com.lgu.abc.core.plugin.proto.user.context;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;

@Component
public class GlobalUserDepartmentChangeListener implements UserDepartmentChangeListenable {

	private final List<UserDepartmentChangeListenable> listenables = new ArrayList<UserDepartmentChangeListenable>();
	
	/**
	 * Register local implementation to this class.
	 * @param listenable the local implementation of user department change listener
	 */
	public synchronized void add(UserDepartmentChangeListenable listenable) {
		listenables.add(listenable);
	}
	
	@Override
	public void changing(User oldUser, User newUser) {
		for (UserDepartmentChangeListenable listener : listenables) {
			listener.changing(oldUser, newUser);
		}
	}

	@Override
	public void changed(User oldUser, User newUser) {
		for (UserDepartmentChangeListenable listener : listenables) {
			listener.changed(oldUser, newUser);
		}
	}

}
