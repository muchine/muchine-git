package com.lgu.abc.core.module.domain;

import org.apache.commons.lang.Validate;
import org.thymeleaf.util.StringUtils;

import lombok.Data;

/**
 * This class contains option configuration components.
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 *
 */
public @Data class ModuleOption {
	
	private final ModuleComponent companyOption;
	
	private final ModuleComponent userOption;
	
	/**
	 * Create module option by using option configuration uris
	 * @param companyOptionUri the company option configuration uri
	 * @param userOptionUri the user option configuration uri
	 */
	public ModuleOption(String companyOptionUri, String userOptionUri) {
		this(component(companyOptionUri), component(userOptionUri));
	}
	
	/**
	 * Create module option for company and user configuration
	 * @param companyOption the company option configuration component
	 * @param userOption the user option configuration component
	 */
	public ModuleOption(ModuleComponent companyOption, ModuleComponent userOption) {
		Validate.notNull(companyOption, "company option component is null.");
		Validate.notNull(userOption, "user option component is null.");
		
		this.companyOption = companyOption;
		this.userOption = userOption;
	}
	
	private static ModuleComponent component(String uri) {
		return StringUtils.isEmpty(uri) ? NullModuleComponent.instance() : new ModuleComponent(uri);
	}
		
}
