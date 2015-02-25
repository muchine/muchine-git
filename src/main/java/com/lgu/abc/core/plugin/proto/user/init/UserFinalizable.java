package com.lgu.abc.core.plugin.proto.user.init;

import com.lgu.abc.core.prototype.org.user.User;

public interface UserFinalizable {

	/**
	 * Finalize all user's environment and documents.
	 * @param user the user who has been terminated by the system
	 */
	void finalize(User user);
	
	/**
	 * Reset all user's environment and documents. It's different from finalization in the aspect that all user environment
	 * is cleared and reset like the one from user account initialization. 
	 * @param user the user who has been reset by the system
	 */
	void reset(User user);
	
}
