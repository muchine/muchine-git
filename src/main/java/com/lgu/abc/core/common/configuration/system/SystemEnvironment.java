package com.lgu.abc.core.common.configuration.system;

import org.springframework.core.env.Environment;

public class SystemEnvironment {

	private final SessionEnvironment session;
	
	private final IntegrationEnvironment integration;
	
	private final ContactEnvironment contact;
	
	private final ProxyEnvironment proxy;
	
	public SystemEnvironment(Environment env) {
		this.session = new SessionEnvironment(env); 
		this.integration = new IntegrationEnvironment(env);
		this.contact = new ContactEnvironment(env);
		this.proxy = new ProxyEnvironment(env);
	}
	
	public SessionEnvironment session() {
		return session;
	}
	
	public IntegrationEnvironment integration() {
		return integration;
	}
	
	public ContactEnvironment contact() {
		return contact;
	}
	
	public ProxyEnvironment proxy() {
		return proxy;
	}
	
}
