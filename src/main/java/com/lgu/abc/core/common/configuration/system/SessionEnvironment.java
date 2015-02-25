package com.lgu.abc.core.common.configuration.system;

import org.springframework.core.env.Environment;

public class SessionEnvironment {

	private static final String LIFETIME = "system.session.lifetime";
	
	private final int lifetime;
	
	public SessionEnvironment(Environment env) {
		this.lifetime = Integer.parseInt(env.getProperty(LIFETIME));
	}
	
	public int lifetime() {
		return lifetime;
	}
	
}
