package com.lgu.abc.core.support.formatter;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class DateTimeFormatterTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private DateFormatter formatter = new DateFormatter(); 
		
	@Test
	public void testPrint() {
		logger.debug("print: " + formatter.print(new Date(), null));
	}
	
	@Test
	public void testParse() throws Exception {
		logger.debug("parse: " + formatter.parse("2013-12-12 10:00:05", null));
	}
	
	@Test
	public void testParseyyyyMMddHHmmss() throws Exception {
		DateFormatter formatter = new DateFormatter("yyyyMMddHHmmss");
		logger.debug("parse: " + formatter.parse("20131212100005", null));
	}
	
}
