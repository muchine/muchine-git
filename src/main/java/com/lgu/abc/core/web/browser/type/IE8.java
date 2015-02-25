package com.lgu.abc.core.web.browser.type;


public class IE8 extends IE {

	public IE8() {
		super("MSIE 8");
	}

	/**
	 * The IE8 specification is satisfied when user agent string contains MSIE 8 or the user sends a HTTP request in compatibility mode.
	 * When compatibility mode is on, we can't take the exact browser version from the user agent. Therefore, we regard it as IE8 
	 * since the compatibility mode is supported by Internet Explorer 8 or above. 
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return super.isSatisfiedBy(object) || isCompatibilityMode(object.toString());
	}
	
}
