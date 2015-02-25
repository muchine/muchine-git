package com.lgu.abc.core.plugin.ui.notification.manager;

import com.lgu.abc.core.base.plugin.ModulePluggable;

public interface NotificationManager extends ModulePluggable {

	/**
	 * Synchronize notification count on all relevant destinations like a messenger. 
	 * @param userId the identifier of user who requests count synchronization
	 */
	void synchronize(String userId);
	
}
