package com.lgu.abc.core.web.browser.type;

import com.lgu.abc.core.web.browser.BrowserSpecification;

public abstract class IE extends BrowserSpecification {

	protected static final String IE7_USER_AGENT = "MSIE 7";
	
	private final String TOKEN = "Trident";
	
	protected IE(String version) {
		super(version);
	}

	/**
	 * See if a user browser is in compatibility model. When a user agent string contains "MSIE 7" but has a "Trident" token,
	 * we can make sure the browser has made a request in compatibility mode.
	 * 
	 * @param agent user agent string
	 * @see http://blogs.msdn.com/b/ie/archive/2009/01/09/the-internet-explorer-8-user-agent-string-updated-edition.aspx 
	 */
	protected final boolean isCompatibilityMode(String agent) {
		return agent.contains(IE7_USER_AGENT) && agent.contains(TOKEN);
	}
	
}
