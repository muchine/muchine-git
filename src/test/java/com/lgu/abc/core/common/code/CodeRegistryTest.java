package com.lgu.abc.core.common.code;

import org.junit.Test;

import com.lgu.abc.core.common.code.CodeRegistry;
import com.lgu.abc.core.common.code.Color;
import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.test.common.base.BaseTest;

public class CodeRegistryTest extends BaseTest {

	@Test
	public void testRegistry() {
		logger.debug("Colors: " + CodeRegistry.codes(new ClassId("99001")));		
		
		Color c = new Color();
		c.setCode("01");
		logger.debug("color: " + c);
	}
	
}
