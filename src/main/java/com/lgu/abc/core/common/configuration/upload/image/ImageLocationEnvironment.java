package com.lgu.abc.core.common.configuration.upload.image;

import org.springframework.core.env.Environment;

public class ImageLocationEnvironment {

	private static final String LOGO = "upload.logo.sublocation";
	private static final String BANNER = "upload.banner.sublocation";
	
	private static final String TEMP = "upload.image.temp.location";
	private static final String PERM = "upload.image.real.location";
	
	private final Environment env;
	
	ImageLocationEnvironment(Environment env) {
		this.env = env;
	}
	
	public String logo() {
		return env.getProperty(LOGO);
	}
	
	public String banner() {
		return env.getProperty(BANNER);
	}
	
	public String temp() {
		return env.getProperty(TEMP);
	}
	
	public String permanent() {
		return env.getProperty(PERM);
	}

	public String large() {
		return permanent();
	}
	
}
