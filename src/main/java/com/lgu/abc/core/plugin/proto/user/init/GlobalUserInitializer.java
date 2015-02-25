package com.lgu.abc.core.plugin.proto.user.init;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;

/**
 * Global implementation that contains all local implementation class of each module. The local implementation in each module should 
 * take the reference of this class via Spring bean injection and add itself in order to get user initialization event notification.
 * @author Sejoon Lim
 * @since 2014. 3. 10.
 *
 */
@Component
public class GlobalUserInitializer implements UserInitializable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Getter
	private Set<UserInitializable> initilalizables = new HashSet<UserInitializable>();
	
	@Autowired
	private GlobalUserInitializer(GlobalUserFinalizer terminator) {
		terminator.add(this);
	}
	
	public synchronized void add(UserInitializable initializable) {
		initilalizables.add(initializable);
	}

	@Override
	public void initialize(User user) {
		logger.info("initialize user... user: " + user.getId());
		for (UserInitializable initializable : initilalizables) {
			initializable.initialize(user);
		}
	}

	@Override
	public void update(User oldUser, User newUser) {
		logger.info("update user... user: " + newUser.getId());
		for (UserInitializable initializable : initilalizables) {
			initializable.update(oldUser, newUser);
		}
	}

	@Override
	public void finalize(User user) {
		for (UserInitializable initializable : initilalizables) {
			initializable.finalize(user);
		}
	}
	
	@Override
	public void reset(User user) {
		for (UserInitializable initializable : initilalizables) {
			initializable.reset(user);
		}
	}
	
}
