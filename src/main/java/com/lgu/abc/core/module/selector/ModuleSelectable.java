package com.lgu.abc.core.module.selector;

import com.lgu.abc.core.module.Module;

/**
 * This interface represents how to select/pick modules from all registered modules. 
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public interface ModuleSelectable {

	/**
	 * Return true if the module can be selected.
	 * @param module the module to find out whether can be selected
	 * @return true if the module can be selected
	 */
	boolean canSelect(Module module);
	
}
