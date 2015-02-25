package com.lgu.abc.core.web.interceptor.component;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.interceptor.uri.UriProtector;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.support.WebUtils;

@Component
public class ComponentInterceptor implements HandlerInterceptor {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final Set<InterceptorFilter> filters = new HashSet<InterceptorFilter>();
	
	public synchronized void add(InterceptorFilter filter) {
		filters.add(filter);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,	HttpServletResponse response, Object handler) throws Exception {
		if (!canHandle(request)) return true;
		
		for (InterceptorFilter filter : filters) {
			if (!filter.process(request, response)) return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,	ModelAndView modelAndView) 
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
	
	private boolean canHandle(HttpServletRequest request) {
		return !UriProtector.allows(request) && SessionManager.hasSession(request) && !WebUtils.isAjax(request);
	}
	
}
