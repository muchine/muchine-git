package com.lgu.abc.core.common.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.test.common.base.BaseTest;

public class ConfigurationTest extends BaseTest {

	@Autowired
	private AbcConfig config;
	
	@Test
	public void testSingletonInstance() {
		AbcConfig configuration = AbcConfig.instance();
		String auth = configuration.authentication().server();
		
		logger.debug("auth: " + auth);
		assertThat(auth, is(notNullValue()));
	}
	
}
