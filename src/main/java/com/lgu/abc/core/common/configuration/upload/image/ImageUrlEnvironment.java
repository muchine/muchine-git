package com.lgu.abc.core.common.configuration.upload.image;

import org.springframework.core.env.Environment;

public class ImageUrlEnvironment {

	private static final String TEMP = "upload.image.temp.baseurl";
	private static final String PERM = "upload.image.real.baseurl";
	
	private final Environment env;
	
	ImageUrlEnvironment(Environment env) {
		this.env = env;
	}
	
	public String temp() {
		return env.getProperty(TEMP);
	}
	
	public String permanent() {
		return env.getProperty(PERM);
	}
	
}
