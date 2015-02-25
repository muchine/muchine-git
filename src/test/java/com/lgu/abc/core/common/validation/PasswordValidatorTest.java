package com.lgu.abc.core.common.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class PasswordValidatorTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	private PasswordValidator validator = new PasswordValidator();
	
	@Test
	public void testUpperAndLowerAndDigitAndSpecial() {
		assertThat(validator.isValid("Welcome1@", null), is(true));
	}
	
	@Test
	public void testUpperAndLowerAndDigit() {
		assertThat(validator.isValid("Welcome1", null), is(true));
	}
	
	@Test
	public void testUpperAndLowerAndSpecial() {
		char[] chars = new char[] {
				'~', '`', '!', '@', '#', '$', '%', '^', '&', '*', 
				'(', ')', '_', '-', '+', '=', '{', '[', '}', ']',
				'|','\\', ':', ';', '"','\'', '<', ',', '>', '.',
				'?', '/'
		};
		
		for (char c : chars) testSpecialCharacters(c);				
	}
	
	private void testSpecialCharacters(char specialChar) {
		logger.debug("test character: " + specialChar);
		assertThat(validator.isValid("Welcome" + specialChar, null), is(true));
	}
		
	@Test
	public void testUpperAndDigitAndSpecial() {
		assertThat(validator.isValid("WELCOME1@", null), is(true));
	}
	
	@Test
	public void testLowerAndDigitAndSpecial() {
		assertThat(validator.isValid("welcome1@", null), is(true));
	}
	
	@Test
	public void testLackofLength() {
		assertThat(validator.isValid("welcomed", null), is(false));
	}
	
	@Test
	public void testUpperAndLower() {
		assertThat(validator.isValid("Welcomed", null), is(false));
	}
	
	@Test
	public void testUpperAndDigit() {
		assertThat(validator.isValid("WELCOME1", null), is(false));
	}
	
	@Test
	public void testUpperAndSpecial() {
		assertThat(validator.isValid("WELCOME!", null), is(false));
	}
	
}
