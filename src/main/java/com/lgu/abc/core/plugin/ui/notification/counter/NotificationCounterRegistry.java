package com.lgu.abc.core.plugin.ui.notification.counter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

/**
 * A registry that contains all local notification counter implementations.
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
@Component
public class NotificationCounterRegistry {

	private static final Set<NotificationCounter> counters = new HashSet<NotificationCounter>();
	
	/**
	 * Add a local notification counter implementation to this registry.
	 * @param counter a local implementation to add
	 */
	public synchronized void add(NotificationCounter counter) {
		PluggableUtils.add(counter, counters);
	}
	
	public static NotificationCounter find(String counterId, Company company) {
		return PluggableUtils.find(counterId, company, counters);
	}
	
	/**
	 * Find all of enabled notification counters for the given company. A counter is enabled if and only if the module of it is enabled.
	 * @param company the company to find notification counters
	 * @return  all of enabled notification counters
	 */
	public static List<NotificationCounter> enabled(Company company) {
		return PluggableUtils.enabled(company, counters);
	}
	
	/**
	 * Return all of notification counter implementations contained in this registry. 
	 * @return all of notification counters
	 */
	public static Set<NotificationCounter> all() {
		return PluggableUtils.all(counters);
	}
		
}
