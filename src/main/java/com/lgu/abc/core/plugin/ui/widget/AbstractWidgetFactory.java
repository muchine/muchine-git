package com.lgu.abc.core.plugin.ui.widget;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.module.GlobalModuleRegistry;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.plugin.ui.widget.factory.WidgetFactory;
import com.lgu.abc.core.prototype.org.company.Company;

public abstract class AbstractWidgetFactory implements WidgetFactory {

	@Autowired
	private GlobalModuleRegistry registry;
	
	private final String moduleId;
	
	protected AbstractWidgetFactory(WidgetRegistry factory, String moduleId) {
		factory.add(this);
		this.moduleId = moduleId;
	}
	
	@Override
	public Module module(Company company) {
		return registry.find(company, moduleId);
	}

}
