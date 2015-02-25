package com.lgu.abc.core.web.interceptor.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.interceptor.InterceptorFilterComparator;
import com.lgu.abc.core.web.interceptor.uri.UriProtector;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

	private static final List<InterceptorFilter> filters = new ArrayList<InterceptorFilter>();
	
	public synchronized void add(InterceptorFilter filter) {
		filters.add(filter);
		Collections.sort(filters, new InterceptorFilterComparator());
	}
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (UriProtector.allows(request)) return true;
		
		for (InterceptorFilter filter : filters) {
			if (!filter.process(request, response)) return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)	throws Exception {

	}

}
