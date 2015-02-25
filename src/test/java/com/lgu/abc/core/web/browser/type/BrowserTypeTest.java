package com.lgu.abc.core.web.browser.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.web.browser.BrowserSpecification;
import com.lgu.abc.core.web.browser.UserAgents;

public class BrowserTypeTest {

	@Test
	public void testIE6() {
		BrowserSpecification spec = new IE6();
		assertThat(spec.isSatisfiedBy(UserAgents.IE6), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE7), is(false));
	}
	
	@Test
	public void testIE7() {
		BrowserSpecification spec = new IE7();
		assertThat(spec.isSatisfiedBy(UserAgents.IE7), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE7_COMPATIBLE), is(false));
		assertThat(spec.isSatisfiedBy(UserAgents.IE8), is(false));
	}
	
	@Test
	public void testIE8() {
		BrowserSpecification spec = new IE8();
		assertThat(spec.isSatisfiedBy(UserAgents.IE8), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE7_COMPATIBLE), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE9), is(false));
	}
	
	@Test
	public void testIE9() {
		BrowserSpecification spec = new IE9();
		assertThat(spec.isSatisfiedBy(UserAgents.IE9), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE10), is(false));
	}
	
	@Test
	public void testIE10() {
		BrowserSpecification spec = new IE10();
		assertThat(spec.isSatisfiedBy(UserAgents.IE10), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE11), is(false));
	}
	
	@Test
	public void testIE11() {
		BrowserSpecification spec = new IE11();
		assertThat(spec.isSatisfiedBy(UserAgents.IE11), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE10), is(false));
	}
	
	@Test
	public void testChrome() {
		BrowserSpecification spec = new Chrome();
		assertThat(spec.isSatisfiedBy(UserAgents.CHROME), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE11), is(false));
	}
	
	@Test
	public void testSafari() {
		BrowserSpecification spec = new Safari();
		assertThat(spec.isSatisfiedBy(UserAgents.SAFARI), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.CHROME), is(false));
		assertThat(spec.isSatisfiedBy(UserAgents.IE11), is(false));
	}
	
	@Test
	public void testFirefox() {
		BrowserSpecification spec = new Firefox();
		assertThat(spec.isSatisfiedBy(UserAgents.FIREFOX), is(true));
		assertThat(spec.isSatisfiedBy(UserAgents.IE11), is(false));
	}
	
}
