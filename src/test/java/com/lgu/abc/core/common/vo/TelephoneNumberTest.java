package com.lgu.abc.core.common.vo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.vo.contact.domain.PhoneNumber;

public class TelephoneNumberTest {

	@Test
	public void test() throws Exception {
		PhoneNumber number = new PhoneNumber("070-4080-1234");
		
		assertThat(number.getArea(), is("070"));
		assertThat(number.getRegion(), is("4080"));
		assertThat(number.getLocal(), is("1234"));
	}
	
}
