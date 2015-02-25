package com.lgu.abc.core.plugin.ui.quick;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

/**
 * A registry that contains all local quick menu implementations.
 * @author Sejoon Lim
 * @since 2014. 3. 18.
 *
 */
@Component
public class QuickMenuRegistry {

	private static final Set<QuickMenu> menus = new HashSet<QuickMenu>();
	
	private QuickMenuRegistry() {}
	
	/**
	 * Add a local quick menu implementation to this registry.
	 * @param menu a local implementation to add
	 */
	public synchronized void add(QuickMenu menu) {
		PluggableUtils.add(menu, menus);
	}
	
	/**
	 * Find a quick menu implementation by menu id and company. If the module for the given company is disabled, the method returns null
	 * even though the quick menu exists. 
	 * @param menuId the id of quick menu
	 * @param company the company to find a quick menu
	 * @return
	 */
	public static QuickMenu find(String menuId, Company company) {
		return PluggableUtils.find(menuId, company, menus);
	}
	
	/**
	 * Find all of enabled quick menus for the given company. A quick menu is enabled if and only if the module of it is enabled.
	 * @param company the company to find quick menus
	 * @return all enabled quick menus
	 */
	public static List<QuickMenu> enabled(Company company) {
		return PluggableUtils.enabled(company, menus);
	}
	
	/**
	 * Return all of quick menu implementations contained in this registry. 
	 * @return all of quick menus
	 */
	public static Set<QuickMenu> all() {
		return PluggableUtils.all(menus);
	}
	
}
