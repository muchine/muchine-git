package com.lgu.abc.core.common.vo.contact.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ZipCodeTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void testZipConstructorWithFirstAndLastArguments() {
		ZipCode zip = new ZipCode("123", "456");
		logger.debug("zip: " + zip);
		assertThat(zip.toString(), is("123-456"));
	}
	
	@Test
	public void testZipConstructorWithStringArgument() {
		ZipCode zip = new ZipCode("123-456");
		logger.debug("zip: " + zip);
		assertThat(zip.toString(), is("123-456"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testZipConstructorWithInvalidLength() {
		ZipCode zip = new ZipCode("1234", "567");
		assertThat(zip.isEmpty(), is(true));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testZipConstructorWithInvalidCharacters() {
		ZipCode zip = new ZipCode("12a", "567");
		assertThat(zip.isEmpty(), is(true));
	}
	
}
