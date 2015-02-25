package com.lgu.abc.core.web.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.lgu.abc.core.web.browser.BrowserSpecification;
import com.lgu.abc.core.web.interceptor.uri.UriProtector;
import com.lgu.abc.core.web.support.WebUtils;

class EntryPointLogger {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void logEntryPoint(HttpServletRequest request, String message) {
		String uri = WebUtils.uri(request);
		if (!canLog(uri)) return;
		
		StringBuilder builder = new StringBuilder()
			.append(message).append(request.getMethod()).append(" ").append(uri)
			.append(" (")
			.append(WebUtils.ip(request))
			.append(" - ").append(getBrowserName(request))
			.append(")");
		
		logger.info(builder.toString());
	}
	
	public void logParameters(HttpServletRequest request) {
		if (!canLog(request)) return;
		
		Map<String, String[]> params = request.getParameterMap();
		StringBuilder builder = new StringBuilder("params:\n");
		for (String key : params.keySet()) {
			String value = StringUtils.arrayToCommaDelimitedString(params.get(key));
			builder.append(key).append(": ").append(value).append("\n");
		}
		
		logger.info(builder.toString());
	}
	
	public void logException(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		
		logger.error(writer.toString());
	}
	
	private boolean canLog(HttpServletRequest request) {
		return canLog(WebUtils.uri(request));		
	}
	
	private boolean canLog(String uri) {
		return !uri.startsWith(UriProtector.RESOURCE_URI);
	}
	
	private String getBrowserName(HttpServletRequest request) {
		BrowserSpecification spec = WebUtils.browser(request);
		return spec == null ? "unknown" : spec.getClass().getSimpleName();
	}
	
}
