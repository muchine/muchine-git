package com.lgu.abc.core.common.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NameValidatorTest {

	private NameValidator validator = new NameValidator();
	
	@Test
	public void testValid() {
		assertThat(validator.isValid("sejoon", null), is(true));
	}
	
	@Test
	public void testCaretIncluded() {
		assertThat(validator.isValid("se^joon", null), is(false));
	}
	
	@Test
	public void testCommaIncluded() {
		assertThat(validator.isValid("se,joon", null), is(false));
	}
	
}
