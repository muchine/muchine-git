package com.lgu.abc.core.common.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.vo.time.day.Anniversary;

public class DayValidatorTest {

	private DayValidator validator = new DayValidator();
	
	@Test
	public void testValidDay() {
		Anniversary anniversary = new Anniversary("20140101");
		assertThat(validator.isValid(anniversary, null), is(true));
	}
	
	@Test
	public void testValidDelimitedDay() {
		Anniversary anniversary = new Anniversary("2014/01/01");
		assertThat(validator.isValid(anniversary, null), is(true));
	}
	
	@Test
	public void testInvalidDay() {
		Anniversary anniversary = new Anniversary("2014.01.01");
		assertThat(validator.isValid(anniversary, null), is(false));
	}
	
}
