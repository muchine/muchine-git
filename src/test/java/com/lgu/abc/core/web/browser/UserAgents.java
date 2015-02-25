package com.lgu.abc.core.web.browser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;

public class UserAgents {
	
	public static final String HEADER = "User-Agent";

	public static final String IE6 = "Mozilla/4.0 (compatible; MSIE 6.1; Windows XP)";
	public static final String IE7 = "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)";
	public static final String IE7_COMPATIBLE = "Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.2; Trident/4.0;";
	public static final String IE8 = "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0;";
	public static final String IE9 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 7.1; Trident/5.0)";
	public static final String IE10 = "Mozilla/4.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/5.0)";
	public static final String IE11 = "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko";
	
	public static final String SAFARI = "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) " + 
				"Version/6.0 Mobile/10A5355d Safari/8536.25";
	public static final String CHROME = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
				"Chrome/32.0.1667.0 Safari/537.36";
	public static final String FIREFOX = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
	
	public static void setUserAgent(String agent, MockHttpServletRequest request) {
		request.addHeader(HEADER, agent);
	}
	
	public static HttpServletRequest create(String agent) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(HEADER, agent);
		
		return request;
	}
	
}
