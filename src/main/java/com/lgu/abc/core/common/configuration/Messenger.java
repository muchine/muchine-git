package com.lgu.abc.core.common.configuration;

import org.springframework.core.env.Environment;

public final class Messenger {

	private static final String DOMAIN = "msng.domain";
	
	private final Environment env;
	
	public Messenger(Environment env) {
		this.env = env;
	}
	
	public String domain() {
		return env.getProperty(DOMAIN);
	}
	
}
