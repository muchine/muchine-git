package com.lgu.abc.core.web.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.lgu.abc.core.web.browser.BrowserRegistry;
import com.lgu.abc.core.web.browser.BrowserSpecification;
import com.lgu.abc.core.web.browser.UserAgents;
import com.lgu.abc.core.web.browser.type.Chrome;
import com.lgu.abc.core.web.browser.type.IE8;
import com.lgu.abc.core.web.browser.type.Safari;

public class WebUtilsTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void testDomain() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.abc.com");
		
		assertThat(WebUtils.domain(request), is("www.abc.com"));
	}
	
	@Test
	public void testDomainWithPort() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("www.abc.com");
		request.setServerPort(8080);
		
		assertThat(WebUtils.domain(request), is("www.abc.com"));
	}
	
	@Test
	public void testBrowser() {
		// When
		BrowserSpecification spec = WebUtils.browser(request(UserAgents.SAFARI));
		
		// Then
		logger.debug("spec: " + spec.getClass());
		assertThat(spec instanceof Safari, is(true));
	}
	
	@Test
	public void testSatisfiesIE() {
		final BrowserSpecification ie = BrowserRegistry.ie();
		
		assertThat(WebUtils.satisfies(request(UserAgents.CHROME), ie), is(false));
		assertThat(WebUtils.satisfies(request(UserAgents.SAFARI), ie), is(false));
		assertThat(WebUtils.satisfies(request(UserAgents.FIREFOX), ie), is(false));
		
		assertThat(WebUtils.satisfies(request(UserAgents.IE6), ie), is(true));
		assertThat(WebUtils.satisfies(request(UserAgents.IE7), ie), is(true));
		assertThat(WebUtils.satisfies(request(UserAgents.IE8), ie), is(true));
		assertThat(WebUtils.satisfies(request(UserAgents.IE9), ie), is(true));
		assertThat(WebUtils.satisfies(request(UserAgents.IE10), ie), is(true));
		assertThat(WebUtils.satisfies(request(UserAgents.IE11), ie), is(true));
	}
	
	@Test
	public void testSatisfiesChrome() {
		final BrowserSpecification chrome = new Chrome();
		assertThat(WebUtils.satisfies(request(UserAgents.CHROME), chrome), is(true));
		assertThat(WebUtils.satisfies(request(UserAgents.SAFARI), chrome), is(false));
	}
	
	@Test
	public void testIE8WithMultipleMSIEString() {
		String agent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; GTB7.5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)";
		BrowserSpecification spec = WebUtils.browser(request(agent));
		
		// Then
		logger.debug("spec: " + spec.getClass());
		assertThat(spec instanceof IE8, is(true));
	}
	
	@Test
	public void testIE8WithIE6String() {
		String agent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2; .NET4.0C; .NET4.0E)";
		BrowserSpecification spec = WebUtils.browser(request(agent));
		
		// Then
		logger.debug("spec: " + spec.getClass());
		assertThat(spec instanceof IE8, is(true));
	}
	
	private HttpServletRequest request(String userAgent) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(WebUtils.USER_AGENT_NAME, userAgent);
				
		return request;
	}
	
}
