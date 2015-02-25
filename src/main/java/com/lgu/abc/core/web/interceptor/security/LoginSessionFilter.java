package com.lgu.abc.core.web.interceptor.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.support.WebUtils;

@Component
public final class LoginSessionFilter implements InterceptorFilter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private AbcConfig config;
	
	@Autowired
	private LoginSessionFilter(SecurityInterceptor interceptor) {
		interceptor.add(this);
	}
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.trace("filtering login session...");
		if (SessionManager.hasSession(request)) return true;
		
		logger.trace("redirect user since having no login session... domain: " + WebUtils.domain(request));
		
		final String redirected = config.authentication().redirected(WebUtils.domain(request));
		if (WebUtils.isAjax(request))
			response.setStatus(HttpStatus.SC_MOVED_TEMPORARILY);
		else
			response.sendRedirect(redirected);
		
		return false;
	}
	
	@Override
	public int order() {
		return 4;
	}

}
