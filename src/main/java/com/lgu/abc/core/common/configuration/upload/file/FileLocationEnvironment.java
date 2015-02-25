package com.lgu.abc.core.common.configuration.upload.file;

import org.springframework.core.env.Environment;

public class FileLocationEnvironment {

	private static final String TEMP = "upload.temp.location";
	private static final String PERM = "upload.real.location";
	private static final String LARGE = "upload.large.location";
	
	private final Environment env;
	
	FileLocationEnvironment(Environment env) {
		this.env = env;
	}
	
	public String temp() {
		return env.getProperty(TEMP);
	}
	
	public String permanent() {
		return env.getProperty(PERM);
	}
	
	public String large() {
		return env.getProperty(LARGE);
	}
	
}
