package com.lgu.abc.core.common.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class LoginIdValidatorTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginIdValidator validator = new LoginIdValidator();
	
	@Test
	public void testValidLoginId() {
		assertThat(validator.isValid("sejoon1.lim", null), is(true));
	}
	
	@Test
	public void testUnderscoreIncluded() {
		assertThat(validator.isValid("sejoon1_lim", null), is(true));
	}
	
	@Test
	public void testHypehnIncluded() {
		assertThat(validator.isValid("sejoon1-lim", null), is(true));
	}
	
	@Test
	public void testUppercaseIncluded() {
		assertThat(validator.isValid("Sejoon", null), is(false));
	}
	
	@Test
	public void testSpecialCharactersIncluded() {
		assertThat(validator.isValid("sejoon#", null), is(false));
	}
	
	@Test
	public void testLackofLength() {
		assertThat(validator.isValid("se", null), is(false));
	}
	
	@Test
	public void testEmptyValue() {
		assertThat(validator.isValid("", null), is(false));
	}
	
}
