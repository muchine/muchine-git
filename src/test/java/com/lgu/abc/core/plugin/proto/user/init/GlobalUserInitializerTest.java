package com.lgu.abc.core.plugin.proto.user.init;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.plugin.proto.user.init.GlobalUserInitializer;
import com.lgu.abc.core.plugin.proto.user.init.UserInitializable;
import com.lgu.abc.test.common.base.BaseTest;

public class GlobalUserInitializerTest extends BaseTest {

	@Autowired
	private GlobalUserInitializer initializer;
	
	@Test
	public void testShowInitializables() {
		for (UserInitializable initializable : initializer.getInitilalizables())
			logger.debug("initializable: " + initializable.getClass().getSimpleName());
	}
	
}
