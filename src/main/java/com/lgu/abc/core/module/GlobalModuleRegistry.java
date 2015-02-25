package com.lgu.abc.core.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.module.exception.ModuleNotFoundException;
import com.lgu.abc.core.module.selector.ModuleSelectable;
import com.lgu.abc.core.prototype.org.company.Company;

/**
 * A global registry that contains all of local module implementations.
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
@Component
public final class GlobalModuleRegistry {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private GlobalModuleFactory factory;
	
	/**
	 * Find a module that has a given module id for the company. It doesn't matter whether a module is enabled or disabled.
	 * @param company the company that owns a module
	 * @param moduleId the id of module to find
	 * @return the module implementation matched with the given module id
	 * @throws ModuleNotFoundException in case when a module is not found in the registry.
	 */
	public Module find(Company company, String moduleId) throws ModuleNotFoundException {
		for (Module module : all(company)) {
			if (module.id().equals(moduleId)) return module;
		}
		
		throw new ModuleNotFoundException();
	}
	
	/**
	 * Find all enabled modules selected by a given selectable for the company.
	 * @param company the company that owns a module
	 * @param selectable the filter to select specific modules
	 * @return the enabled modules selected by a selectable filter 
	 */
	public List<Module> enabled(Company company, ModuleSelectable selectable) {
		List<Module> enabled = new ArrayList<Module>();
		
		for (Module module : all(company)) {
			logger.trace("module: " + module + ", enabled: " + module.isEnabled());
			if (ModuleUtils.isEnabled(module) && selectable.canSelect(module)) enabled.add(module);
		}
		
		logger.trace("enabled: " + enabled);
		return enabled;
	}
	
	/**
	 * Find all enabled modules for the company
	 * @param company the company that owns a module
	 * @return all of the enabled modules
	 */
	public List<Module> enabled(Company company) {
		return enabled(company, new ModuleSelectable() {

			@Override
			public boolean canSelect(Module module) {
				return true;
			}
			
		});
	}
	
	/**
	 * Return all modules in the registry for the given company
	 * @param company the company that owns a module
	 * @return all modules contained in the registry
	 */
	public List<Module> all(Company company) {
		return factory.create(company);
	}
		
}
