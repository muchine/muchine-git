package com.lgu.abc.core.plugin.ui.notification.counter;

import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;
import com.lgu.abc.core.common.infra.counter.loader.NotificationContentLoadable;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * A basic implementation of NotificationCounter interface
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public abstract class AbstractNotificationCounter extends AbstractModulePluggable implements NotificationCounter {
	
	/**
	 * Register the concrete NotificationCounter implementation class to the registry and store the module id in order to find a module.
	 * @param registry
	 * @param moduleId
	 */
	protected AbstractNotificationCounter(NotificationCounterRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}

	@Override
	public final ModelAndView content(User actor, int maxContentCount) {
		return loadable(actor, maxContentCount).load();
	}
	
	protected abstract NotificationContentLoadable loadable(User actor, int maxContentCount);
	
}
