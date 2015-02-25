package com.lgu.abc.core.web.browser.type;

public class IE7 extends IE {

	public IE7() {
		super(IE.IE7_USER_AGENT);
	}
	
	@Override
	public boolean isSatisfiedBy(Object object) {
		return super.isSatisfiedBy(object) && !isCompatibilityMode(object.toString());
	}
	
}
