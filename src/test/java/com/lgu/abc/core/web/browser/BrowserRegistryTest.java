package com.lgu.abc.core.web.browser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lgu.abc.core.web.browser.type.Chrome;
import com.lgu.abc.core.web.browser.type.IE7;
import com.lgu.abc.core.web.browser.type.IE9;
import com.lgu.abc.core.web.browser.type.Safari;

public class BrowserRegistryTest {

	@Test
	public void testAll() {
		List<BrowserSpecification> specs = BrowserRegistry.all();
		assertThat(specs.isEmpty(), is(false));
		assertThat(contains(Chrome.class, specs), is(true));
		assertThat(contains(Safari.class, specs), is(true));
		assertThat(contains(IE7.class, specs), is(true));
		assertThat(contains(IE9.class, specs), is(true));
	}
	
	@Test
	public void testIE() {
		BrowserSpecification ie = BrowserRegistry.ie();
		assertThat(ie.isSatisfiedBy(UserAgents.IE6), is(true));
		assertThat(ie.isSatisfiedBy(UserAgents.IE7), is(true));
		assertThat(ie.isSatisfiedBy(UserAgents.IE8), is(true));
		assertThat(ie.isSatisfiedBy(UserAgents.IE9), is(true));
		assertThat(ie.isSatisfiedBy(UserAgents.IE10), is(true));
		assertThat(ie.isSatisfiedBy(UserAgents.IE11), is(true));
		assertThat(ie.isSatisfiedBy("Unknown"), is(true));
		
		assertThat(ie.isSatisfiedBy(UserAgents.SAFARI), is(false));
		assertThat(ie.isSatisfiedBy(UserAgents.CHROME), is(false));
		assertThat(ie.isSatisfiedBy(UserAgents.FIREFOX), is(false));
	}
	
	@Test
	public void testAccept() {
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.IE6)), is(false));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.IE7)), is(false));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.IE8)), is(true));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.IE9)), is(false));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.IE10)), is(true));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.IE11)), is(true));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.SAFARI)), is(true));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.CHROME)), is(true));
		assertThat(BrowserRegistry.accept(UserAgents.create(UserAgents.FIREFOX)), is(true));
	}
	
	@Test
	public void testAcceptCompatibleIE8WithIE6String() {
		String agent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; GTB7.5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)";
		assertThat(BrowserRegistry.accept(UserAgents.create(agent)), is(true));
	}
	
	@Test
	public void testIE8WithIE6String() {
		String agent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2; .NET4.0C; .NET4.0E)";
		assertThat(BrowserRegistry.accept(UserAgents.create(agent)), is(true));
	}
	
	private boolean contains(Class<?> type, List<BrowserSpecification> specs) {
		for (BrowserSpecification spec : specs) {
			if (type.equals(spec.getClass())) return true;
		}
		
		return false;
	}
	
}
