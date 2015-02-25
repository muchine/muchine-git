package com.lgu.abc.core.common.configuration.upload.file;

import org.springframework.core.env.Environment;

public class FileEnvironment {

	private final FileLocationEnvironment location;
	
	public FileEnvironment(Environment env) {
		this.location = new FileLocationEnvironment(env);
	}
	
	public FileLocationEnvironment location() {
		return location;
	}
	
}
