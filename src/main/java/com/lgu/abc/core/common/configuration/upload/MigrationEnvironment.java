package com.lgu.abc.core.common.configuration.upload;

import org.springframework.core.env.Environment;

public class MigrationEnvironment {

	private static final String LOCATION_NAME = "upload.migration.location";
	
	private final String location;
	
	public MigrationEnvironment(Environment env) {
		this.location = env.getProperty(LOCATION_NAME);
	}
	
	public String location() {
		return location;
	}
	
}
