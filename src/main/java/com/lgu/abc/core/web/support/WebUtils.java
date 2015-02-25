package com.lgu.abc.core.web.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgu.abc.core.web.browser.BrowserRegistry;
import com.lgu.abc.core.web.browser.BrowserSpecification;
import com.lgu.abc.core.web.browser.type.IE11;

public class WebUtils {
		
	protected static final Log logger = LogFactory.getLog(WebUtils.class);
	
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
	static final String USER_AGENT_NAME = "User-Agent";
	
	// x-ajax-request header is defined in abc-common.js
	private static final String AJAX_REQUEST = "x-ajax-request";
	
	public static boolean isAjax(HttpServletRequest request) {
		return "true".equals(request.getHeader(AJAX_REQUEST));
	}
	
	public static String ip(HttpServletRequest request) {
		String forwarded = request.getHeader("X-Forwarded-For");
		return forwarded != null ? forwarded : request.getRemoteAddr();
	}
	
	public static String domain(HttpServletRequest request) {
		return request.getServerName();
	}
	
	public static String uri(HttpServletRequest request) {
		return request.getServletPath();
	}
	
	public static String sessionId(HttpServletRequest request) {
		String sessionId = request.getHeader("x-ajax-session");
		logger.trace("user session id: " + sessionId);
		if (!StringUtils.isEmpty(sessionId)) return sessionId;
		
		return request.getSession().getId();
	}
	
	public static BrowserSpecification browser(HttpServletRequest request) {
		final String agent = getUserAgent(request);
		logger.debug("user agent: " + agent);
		
		if (StringUtils.isEmpty(agent)) return null;
		
		for (BrowserSpecification spec : BrowserRegistry.all()) {
			if (spec.isSatisfiedBy(agent)) return spec;
		}
		
		/*
		 * If we can't find out the browser type by known user string so far, we recognize the browser as IE11.
		 * IE11 has a different way to create user agent string comparing with the older IEs. We need more research on this.
		 */
		return new IE11();
	}
	
	public static boolean isMobile(HttpServletRequest request) {
		 String agent = getUserAgent(request);
		 return !StringUtils.isEmpty(agent) && agent.contains("Mobile");
	}
	
	public static boolean satisfies(HttpServletRequest request, BrowserSpecification specification) {
		return specification.isSatisfiedBy(getUserAgent(request));
	}
	
	public static void createJsonResponse(HttpServletResponse response, Object object) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			jsonMapper.writeValue(response.getOutputStream(), object);	
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}		
	}
	
	private static String getUserAgent(HttpServletRequest request) {
		return request.getHeader(USER_AGENT_NAME);
	}
	
}
