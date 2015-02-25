package com.lgu.abc.core.module.onnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.module.GlobalModuleRegistry;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.ModuleUtils;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class OnnetModuleRegistry {

	private static final Set<OnnetModule> modules = new HashSet<OnnetModule>();
	
	@Autowired
	private GlobalModuleRegistry registry;
	
	public synchronized void add(OnnetModule module) {
		if (find(module.code()) != null) throw new IllegalArgumentException("module already exists. code: " + module.code());
		modules.add(module);
	}
	
	public OnnetModule find(String code) {
		for (OnnetModule module : modules) {
			if (module.code().equals(code)) return module;
		}
		
		return null;
	}
	
	public OnnetModule findByModuleId(String moduleId) {
		for (OnnetModule module : modules) {
			if (module.moduleId().equals(moduleId)) return module;
		}
		
		return null;
	}
	
	public List<OnnetModule> enabled(Company company) {
		List<OnnetModule> enabled = new ArrayList<OnnetModule>();
		
		for (OnnetModule m : modules) {
			Module module = registry.find(company, m.moduleId());
			if (ModuleUtils.isEnabled(module)) enabled.add(m);
		}
		
		return enabled;		
	}
	
}
