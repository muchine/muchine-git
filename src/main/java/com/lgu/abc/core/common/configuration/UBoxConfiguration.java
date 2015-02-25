package com.lgu.abc.core.common.configuration;

import org.springframework.core.env.Environment;

import com.lgu.ubox.client.UBoxClient;
import com.lgu.ubox.client.context.DasEnvironment;
import com.lgu.ubox.client.context.UBoxContext;
import com.lgu.ubox.client.context.UBoxEnvironment;

public class UBoxConfiguration {
	
	private static final String UBOX_SERVER 		= "ubox.server.url";
	private static final String UBOX_ID 			= "ubox.auth.id";
	private static final String UBOX_KEY			= "ubox.auth.key";
	
	private static final String DAS_SERVER 		= "das.server.url";
	private static final String DAS_SERVICE_ID 	= "das.service.id";
	private static final String DAS_SSO_API_KEY 	= "das.api.sso.key";

	private final UBoxClient client;
	
	public UBoxConfiguration(Environment env) {
		UBoxEnvironment ubox = getUBoxEnvironment(env);
		DasEnvironment das = getDasEnvironment(env);
		
		this.client = new UBoxClient(new UBoxContext(ubox, das));
	}
	
	public UBoxClient client() {
		return client; 
	}
	
	private UBoxEnvironment getUBoxEnvironment(Environment env) {
		String server = env.getProperty(UBOX_SERVER);
		String authId = env.getProperty(UBOX_ID);
		String authKey = env.getProperty(UBOX_KEY);
		
		return new UBoxEnvironment(server, authId, authKey);
	}
	
	private DasEnvironment getDasEnvironment(Environment env) {
		String server = env.getProperty(DAS_SERVER);
		String serviceId = env.getProperty(DAS_SERVICE_ID);
		String ssoAccessKey = env.getProperty(DAS_SSO_API_KEY);
		
		return new DasEnvironment(server, serviceId, ssoAccessKey);
	}
	
}
