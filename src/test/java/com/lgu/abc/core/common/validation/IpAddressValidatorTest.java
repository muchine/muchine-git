package com.lgu.abc.core.common.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.validation.IpAddressValidator;

public class IpAddressValidatorTest {

	@Test
	public void testValidateIp() {
		IpAddressValidator validator = new IpAddressValidator();
		
		assertThat(validator.isValid("11.1.2.1", null), is(true));
	}
	
}
