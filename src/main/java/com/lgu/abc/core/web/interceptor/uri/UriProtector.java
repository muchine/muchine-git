package com.lgu.abc.core.web.interceptor.uri;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.web.support.WebUtils;


public final class UriProtector {
	
	protected static final Log logger = LogFactory.getLog(UriProtector.class);
	
	public static final String RESOURCE_URI = "/resources/";
	
	private static final String ATTRIBUTE = "unprotected";
	
	private static final String[] UNPROTECTED_URIS = new String[] {
		RESOURCE_URI,
		"/sso",
		"/error",
		"/common/file/download/external",
		"/org/company/option/ui",
		"/mai/api/check.png",
		"/mai/api/getUnreadMailCount",
		"/mai/api/getBigFile",			// Large volume mail download link
		"/apr/doc/readAprDoc.jstl",		// PC 저장시 로컬로 접근하는 URL
		"/integration"
	};
	
	private static boolean contains(String requestUri) {
		for (String uri : UNPROTECTED_URIS) {
			if (requestUri.contains(uri)) return true;
		}

		return false;
	}

	private static boolean bypasses(HttpServletRequest request) {
		Object unprotected = request.getAttribute(ATTRIBUTE);
		return unprotected != null && (Boolean) unprotected;
	}

	public static boolean allows(HttpServletRequest request) {
		if (bypasses(request) || contains(WebUtils.uri(request))) {
			request.setAttribute(ATTRIBUTE, true);
			return true;
		}

		return false;
	}

}
