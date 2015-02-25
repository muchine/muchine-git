package com.lgu.abc.core.common.code;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.code.Color;
import com.lgu.abc.test.common.base.BaseTest;

public class ColorTest extends BaseTest {
	
	@Test
	public void testRandom() {
		Color random = Color.random();
		System.out.println(random);
		
		assertThat(random, is(notNullValue()));
	}
	
}
