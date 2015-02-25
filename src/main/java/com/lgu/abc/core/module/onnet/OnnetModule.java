package com.lgu.abc.core.module.onnet;

/**
 *  A representation of onnet module context used for integrating with a legacy Onnet systems.
 * @author Sejoon Lim
 * @since 2014. 10. 10.
 *
 */
public interface OnnetModule {

	/**
	 * Return the module id matched with this mobile module.
	 * @return the module id
	 */
	String moduleId();
	
	/**
	 * Return a representational code that can be recognized by a mobile application.
	 * @return a mobile module code
	 */
	String code();
	
}
