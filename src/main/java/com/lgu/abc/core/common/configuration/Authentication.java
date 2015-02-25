package com.lgu.abc.core.common.configuration;

import org.springframework.core.env.Environment;

public final class Authentication {

	private static final String SERVER	= "cert.server.ip";
	private static final String LOGIN		= "cert.login.url"; 
	
	private final Environment env;
	
	public Authentication(Environment env) {
		this.env = env;
	}
	
	public String server() {
		return env.getProperty(SERVER);
	}
	
	public String login() {
		return env.getProperty(LOGIN);
	}
	
	public String redirected(String domain) {
		return login() + "?re="+ domain +"/sso/login";	
	}
	
}
