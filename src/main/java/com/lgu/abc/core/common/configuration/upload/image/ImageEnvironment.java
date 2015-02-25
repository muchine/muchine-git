package com.lgu.abc.core.common.configuration.upload.image;

import org.springframework.core.env.Environment;

public class ImageEnvironment {

	private final ImageLocationEnvironment location;
	
	private final ImageUrlEnvironment url;
	
	public ImageEnvironment(Environment env) {
		this.location = new ImageLocationEnvironment(env);
		this.url = new ImageUrlEnvironment(env);
	}
	
	public ImageLocationEnvironment location() {
		return location;
	}
	
	public ImageUrlEnvironment url() {
		return url;
	}
	
}
