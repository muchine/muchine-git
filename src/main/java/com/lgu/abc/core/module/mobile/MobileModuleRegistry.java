package com.lgu.abc.core.module.mobile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.module.onnet.OnnetModule;
import com.lgu.abc.core.module.onnet.OnnetModuleRegistry;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class MobileModuleRegistry {

	@Autowired
	private OnnetModuleRegistry registry;
	
	public synchronized void add(MobileModule module) {
		registry.add(module);
	}
	
	public MobileModule find(String code) {
		OnnetModule module = registry.find(code);
		return isMobileModule(module) ? (MobileModule) module : null;
	}
	
	public MobileModule findByModuleId(String moduleId) {
		OnnetModule module = registry.findByModuleId(moduleId);
		return isMobileModule(module) ? (MobileModule) module : null;
	}
	
	public List<MobileModule> enabled(Company company) {
		List<MobileModule> enabled = new ArrayList<MobileModule>();
		
		List<OnnetModule> found = registry.enabled(company);
		if (CollectionUtils.isEmpty(found)) return enabled;
		
		for (OnnetModule m : registry.enabled(company)) {
			if (isMobileModule(m)) enabled.add((MobileModule) m);
		}
		
		return enabled;
	}
	
	private boolean isMobileModule(OnnetModule module) {
		return module != null && module instanceof MobileModule;
	}
	
}
