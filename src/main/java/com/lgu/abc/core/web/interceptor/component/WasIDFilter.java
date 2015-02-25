package com.lgu.abc.core.web.interceptor.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.web.interceptor.InterceptorFilter;

@Component
public final class WasIDFilter implements InterceptorFilter {

	public static final String ATTRIBUTE = "wasId";
	
	@Autowired
	private AbcConfig configuration;
	
	@Autowired
	private WasIDFilter(ComponentInterceptor interceptor) {
		interceptor.add(this);
	}
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(ATTRIBUTE, configuration.getWasId());
		return true;
	}
	
	@Override
	public int order() {
		return 2;
	}

}
