package com.lgu.abc.core.plugin.ui.notification.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class NotificationManagerRegistry {

	private static final Set<NotificationManager> managers = new HashSet<NotificationManager>();
	
	/**
	 * Add a local notification manager implementation to this registry.
	 * @param manager a local implementation to add
	 */
	public synchronized void add(NotificationManager manager) {
		PluggableUtils.add(manager, managers);
	}
	
	public static NotificationManager find(String managerId, Company company) {
		return PluggableUtils.find(managerId, company, managers);
	}
	
	/**
	 * Find all of enabled notification managers for the given company. A manager is enabled if and only if the module of it is enabled.
	 * @param company the company to find notification managers
	 * @return  all of enabled notification managers
	 */
	public static List<NotificationManager> enabled(Company company) {
		return PluggableUtils.enabled(company, managers);
	}
	
	/**
	 * Return all of notification manager implementations contained in this registry. 
	 * @return all of notification managers
	 */
	public static Set<NotificationManager> all() {
		return PluggableUtils.all(managers);
	}
	
	public static void synchronize(User user) {
		List<NotificationManager> managers = enabled(user.getCompany());
		for (NotificationManager manager : managers) {
			manager.synchronize(user.getId());
		}
	}
		
}
