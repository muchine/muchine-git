package com.lgu.abc.core.plugin.ui.quick;

import com.lgu.abc.core.base.plugin.AbstractModulePluggable;

/**
 * A basic implementation of QuickMenu interface.
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public abstract class AbstractQuickMenu extends AbstractModulePluggable implements QuickMenu {
	
	/**
	 * Register the concrete QuickMenu implementation class to the registry and store the module id in order to find a module.
	 * @param registry the quick menu registry to add the concrete class
	 * @param moduleId the module id for searching module
	 */
	protected AbstractQuickMenu(QuickMenuRegistry registry, String moduleId) {
		super(moduleId);
		registry.add(this);
	}
	
	@Override
	public int order() {
		/*
		 * QuickMenu has separate sorting mechanism so return 0 here. 
		 */
		return 0;
	}

	/**
	 * Return the quick menu should be initialized when a new user has been created. The default value is false so a module
	 * needs to override this method if it wants to initialize quick menu.
	 */
	@Override
	public boolean isInitializedWithUser() {
		return false;
	}

}
