package com.lgu.abc.core.common.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class NumberValidatorTest {

	protected final Log logger = LogFactory.getLog(getClass());
		
	@Test
	public void testOnlyNumbers() {
		assertThat(NumberValidator.isValid("12345", 6), is(true));
	}
	
	@Test
	public void testInvalidLength() {
		assertThat(NumberValidator.isValid("12345", 4), is(false));
	}
	
	@Test
	public void testIncludeAlphabet() {
		assertThat(NumberValidator.isValid("12345a", 6), is(false));
	}
	
	/*
	 * Fix a bug of tokenization when untrimmed string has been saved
	 */
	@Test
	public void testUntrimmedString() {
		String[] parsed = NumberValidator.parse(" 010-1111-2222 ", "-", 3, 4);
		
		logger.debug("parsed: " + parsed[0] + " " + parsed[1] + " " + parsed[2]);		
		assertThat(parsed, is(notNullValue()));
	}
	
}
