package com.lgu.abc.core.web.security.xss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.web.interceptor.uri.UriProtector;
import com.lgu.abc.core.web.support.WebUtils;

public class XssFilter implements Filter {
	
	protected static final Log logger = LogFactory.getLog(XssFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.trace("xss filter working... domain: " + request.getServerName());
		HttpServletRequest casted = (HttpServletRequest) request;
		chain.doFilter(bypasses(casted) ? request : new XssFilteredRequest(request), response);
	}

	@Override
	public void destroy() {
	
	}
	
	private boolean bypasses(HttpServletRequest request) {
		return UriProtector.allows(request) || BypassUriRegistry.contains(WebUtils.uri(request));
	}
	
	static class XssFilteredRequest extends HttpServletRequestWrapper {

		public XssFilteredRequest(ServletRequest request) {
    		super((HttpServletRequest)request);
    	}

    	@Override
    	public String getParameter(String paramName) {
    		return XssSanitizer.sanitize(super.getParameter(paramName));
    	}

    	@Override
    	public String[] getParameterValues(String paramName) {
    		String values[] = super.getParameterValues(paramName);
    		if (values == null) return null;
    		
			for (int i = 0; i < values.length; i++) {
				values[i] = XssSanitizer.sanitize(values[i]);
			}
			
			return values;    		
    	}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> params = new HashMap<String, String[]>(super.getParameterMap());
			
			for (String key : params.keySet()) {
				String[] values = params.get(key);
				if (values == null) continue;
				
				for (int i = 0; i < values.length; i++) {
					values[i] = XssSanitizer.sanitize(values[i]);
				}
				
				params.put(key, values);
			}
			
			return params;
		}
		
    }	

}
