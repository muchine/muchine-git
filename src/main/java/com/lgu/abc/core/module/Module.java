package com.lgu.abc.core.module;

import com.lgu.abc.core.base.plugin.Pluggable;
import com.lgu.abc.core.module.domain.ModuleComponent;
import com.lgu.abc.core.module.domain.ModuleOption;
import com.lgu.abc.core.module.domain.ModuleRenderer;
import com.lgu.abc.core.module.domain.ModuleType;

/**
 * This interface represents an office module functionality like board, calendar, note, etc. This gives a blueprint of module which includes
 * basic information like id and name, service subscription status, enable/disable configuration and so on. All of module-related operations
 * in the system is controlled based on these information.
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public interface Module extends Pluggable {
	
	/**
	 * Return the module type. Module type can be a value like Mywork, Support, Cowork, etc.
	 * @return
	 */
	ModuleType type();
	
	/**
	 * Return true if the module is in subscription. 
	 * @return true if the module is in subscription
	 */
	boolean isOpened();
	
	/**
	 * Return true if the module is enabled by a company administrator.
	 * @return true if the module is enabled by a company administrator
	 */
	boolean isEnabled();
	
	/**
	 * Return the home page uri representing an entry point of module.
	 * @return the home page uri
	 */
	String home();

	/**
	 * Return company and user option information like whether option configuration is provided and option configuration uri if any. 
	 * @return company and user option configuration information
	 */
	ModuleOption option();
	
	/**
	 * Return volume configuration information
	 * @return
	 */
	ModuleComponent volume();
	
	/**
	 * Return statistics information
	 * @return
	 */
	ModuleComponent statistics();
	
	/**
	 * Return renderer
	 */
	ModuleRenderer render();
	
}
