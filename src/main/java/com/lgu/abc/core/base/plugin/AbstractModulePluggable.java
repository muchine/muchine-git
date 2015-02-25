package com.lgu.abc.core.base.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.module.GlobalModuleRegistry;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.prototype.org.company.Company;

public abstract class AbstractModulePluggable implements ModulePluggable {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private GlobalModuleRegistry registry;
	
	private final String moduleId;
	
	protected AbstractModulePluggable(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Override
	public final Module module(Company company) {
		return registry.find(company, moduleId);
	}

}
