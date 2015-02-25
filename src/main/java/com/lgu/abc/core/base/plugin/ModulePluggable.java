package com.lgu.abc.core.base.plugin;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.prototype.org.company.Company;

public interface ModulePluggable extends Pluggable {

	/**
	 * Return the module that has this plugin. If module is disabled, plugin may not be shown.
	 * @param company the company to find a module
	 * @return the module matched with the given company
	 */
	Module module(Company company);
	
}
