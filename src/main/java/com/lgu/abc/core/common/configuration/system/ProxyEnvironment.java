package com.lgu.abc.core.common.configuration.system;

import org.springframework.core.env.Environment;

public class ProxyEnvironment {

	private static final String URL = "system.proxy.url";
	
	private final String url;
	
	public ProxyEnvironment(Environment env) {
		this.url = env.getProperty(URL);
	}
	
	public String url() {
		return url;
	}
	
}
