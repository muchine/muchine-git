package com.lgu.abc.core.module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class GlobalModuleFactory {
		
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String CACHE_NAME = "modules";
	
	private final Set<ModuleFactory> factories = new HashSet<ModuleFactory>();
	
	public synchronized void add(ModuleFactory factory) {
		factories.add(factory);
	}
	
	@Cacheable(value=CACHE_NAME, key="#company.id")
	public List<Module> create(Company company) {
		List<Module> modules = new ArrayList<Module>();
		
		for (ModuleFactory factory : factories) modules.add(factory.create(company));
		return modules;
	}
	
	@CacheEvict(value=CACHE_NAME, key="#company.id")
	public void evict(Company company) {
		logger.info("evict modules cache... company: " + company);
	}
	
}
