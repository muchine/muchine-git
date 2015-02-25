package com.lgu.abc.core.plugin.ui.quick;

import org.junit.Test;

import com.lgu.abc.core.plugin.ui.quick.QuickMenu;
import com.lgu.abc.core.plugin.ui.quick.QuickMenuRegistry;
import com.lgu.abc.test.common.base.BaseTest;

public class QuickMenuRegistryTest extends BaseTest {

	@Test
	public void testAll() {
		for (QuickMenu attachable : QuickMenuRegistry.all()) {
			logger.debug("attachable: " + attachable);
		}
	}
	
}
