package com.lgu.abc.core.common.configuration.system;

import org.springframework.core.env.Environment;

public class ContactEnvironment {

	private static final String MOBILE = "system.contact.mobile";
	private static final String EMAIL = "system.contact.email";
	
	private final String[] mobiles;
	
	private final String[] emails;
	
	public ContactEnvironment(Environment env) {
		String mobile = env.getProperty(MOBILE);
		this.mobiles = mobile.contains(",") ? mobile.split(",") : new String[0];
		
		String email = env.getProperty(EMAIL);
		this.emails = email.contains(",") ? email.split(",") : new String[0];
	}
	
	public String[] mobiles() {
		return mobiles;
	}
	
	public String[] emails() {
		return emails;
	}
	
}
