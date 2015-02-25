package com.lgu.abc.core.common.infra.log;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.test.common.base.BaseTest;

public class LoggerTest extends BaseTest {

	@Autowired
	private LoggerFactory factory;
	
	private Logger logger;
	
	@Override
	public void setup() throws Exception {
		super.setup();
		this.logger = factory.getLog(getClass());
	}

	@Test
	public void testWrite() {
		final String message = "Test message...";
		
		logger.write("100", "LoggerTest", "testWrite", message);
		logger.debug("debug: " + message);
	}
	
}
