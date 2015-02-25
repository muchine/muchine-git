package com.lgu.abc.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InterceptorFilter {

	boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	int order();
	
}
