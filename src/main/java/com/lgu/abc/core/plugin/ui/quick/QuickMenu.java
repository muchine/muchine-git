package com.lgu.abc.core.plugin.ui.quick;

import com.lgu.abc.core.base.plugin.ModulePluggable;

/**
 * A representation of quick menu which is attached to the quick menu bar on the right site of the page. The module that wants to add
 * its own quick menu needs to implement this interface and add a registry. 
 * @author Sejoon Lim
 * @since 2014. 3. 18.
 * @see com.lgu.abc.core.plugin.ui.quick.QuickMenuRegistry
 *
 */
public interface QuickMenu extends ModulePluggable {
	
	/**
	 * Return the uri link to which users are redirected when the quick menu is clicked. The uri is a relative path that does not include
	 * a domain, for example "/attendance", "/address", etc.
	 * @return the uri link of quick menu
	 */
	String uri();
	
	/**
	 * Return true if this quick menu should be initialized when a new user has been created.
	 * @return true if the quick menu needs to be initialized with user together
	 */
	boolean isInitializedWithUser();
	
}
