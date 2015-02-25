package com.lgu.abc.core.web.browser.type;

import com.lgu.abc.core.web.browser.BrowserSpecification;

public class Safari extends BrowserSpecification {

	public Safari() {
		super("Safari");
	}

	/**
	 * Safari browser specification is satisfied when user agent string has "Safari" and does not have "Chrome".
	 * Chrome browser creates user agent string containing "Safari" and "Chrome" so we need to check above to avoid conflicition
	 * with Chrome.
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return super.isSatisfiedBy(object) && !containsChrome(object.toString());
	}
	
	private boolean containsChrome(String agent) {
		return agent.contains(Chrome.USER_AGENT);
	}
	
}
