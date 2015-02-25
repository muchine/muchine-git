package com.lgu.abc.core.web.security.xss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BypassUriRegistry {
	
	protected static final Log logger = LogFactory.getLog(BypassUriRegistry.class);
	
	private static final String[] bypassed = new String[] {
		"/messaging/text"
	};
	
	static boolean contains(String uri) {
		for (String b : bypassed) {
			logger.trace("b: " + b + ", uri: " + uri + ", contains: " + uri.startsWith(b));  
			if (uri.startsWith(b)) return true;
		}
		
		return false;
	}
	
}
