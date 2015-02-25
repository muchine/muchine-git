package com.lgu.abc.core.plugin.proto.user.role;

import java.util.List;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.prototype.org.user.User;

public interface OrganizationalRole extends ModulePluggable {

	/**
	 * read users who has the role associated with departmental context of actor.
	 * @param actor the actor who has issued the read request
	 * @return
	 */
	List<User> read(User actor);
	
	List<User> findAll(User actor);
	
	OrganizationalRoleMatchable matcher(User actor);
	
	String iconUrl();
	
	boolean isDefault();
	
}
