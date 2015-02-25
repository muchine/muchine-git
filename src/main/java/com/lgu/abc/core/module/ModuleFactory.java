package com.lgu.abc.core.module;

import com.lgu.abc.core.prototype.org.company.Company;

/**
 * A representation of module factory to create a module by using company information. A module can have a different status
 * including module subscription status, enable/disable configuration according to company. Therefore, how to get module status
 * of the given company is encapsulated into the local implementation of each module.
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public interface ModuleFactory {

	/**
	 * Create a module for a given company.
	 * @param company the company to find module status and create a module
	 * @return the module implementation for the company
	 */
	Module create(Company company);
	
}
