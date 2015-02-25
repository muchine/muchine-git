package com.lgu.abc.core.module;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.test.common.base.BaseTest;

public class GlobalModuleRegistryTest extends BaseTest {

	@Autowired
	private GlobalModuleRegistry registry;
	
	@Test
	public void testAll() {
		List<Module> modules = registry.all(getSession().getCompany());
		for (Module module : modules) logger.debug(module.id() + ": " + module);
	}

	@Test
	public void testEnabled() {
		List<Module> modules = registry.enabled(getSession().getCompany());
		for (Module module : modules) {
			logger.debug(module.id() + ": " + module);
			assertThat(module.isEnabled(), is(true));
		}
	}
	
}
