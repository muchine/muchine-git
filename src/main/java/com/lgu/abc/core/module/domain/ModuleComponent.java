package com.lgu.abc.core.module.domain;

import lombok.Data;

/**
 * This class represents a component of module. A module component can be anything like user option, company option, volume option, etc. 
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public @Data class ModuleComponent {

	/**
	 * Return true if the component is supported.
	 */
	private final boolean supported;
	
	/**
	 * Return the uri representing the component.
	 */
	private final String uri;
	
	public ModuleComponent(String uri) {
		this(true, uri);
	}
	
	public ModuleComponent(boolean supported, String uri) {
		this.supported = supported;
		this.uri = uri;
	}
	
}
