package com.lgu.abc.core.web.browser;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.web.interceptor.uri.UriProtector;
import com.lgu.abc.core.web.support.WebUtils;

public class BrowserVersionFilter implements Filter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String REDIRECTED = "/error/browser-unsupported";
	
	private static final String BROWSER_VERSION_ATTRIBUTE = "userBrowserVersion";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest casted = (HttpServletRequest) request;
				
		if (bypasses(casted) || accept(casted))
			chain.doFilter(request, response);
		else
			((HttpServletResponse) response).sendRedirect(REDIRECTED);	
	}

	@Override
	public void destroy() {
		
	}
	
	private boolean bypasses(HttpServletRequest request) {
		return UriProtector.allows(request);
	}
	
	private boolean accept(HttpServletRequest request) {
		BrowserSpecification browser = WebUtils.browser(request);
		boolean accepted = BrowserRegistry.accept(browser);
		
		if (accepted) request.setAttribute(BROWSER_VERSION_ATTRIBUTE, browser.getClass().getSimpleName());
		
		return accepted;
	}

}
