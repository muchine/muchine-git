package com.lgu.abc.core.common.configuration.upload;

import org.springframework.core.env.Environment;

import com.lgu.abc.core.common.configuration.upload.file.FileEnvironment;
import com.lgu.abc.core.common.configuration.upload.image.ImageEnvironment;

public class Upload {
	
	private final FileEnvironment file;
	
	private final ImageEnvironment image;
	
	private final MigrationEnvironment migration;
	
	public Upload(Environment env) {
		this.file = new FileEnvironment(env);
		this.image = new ImageEnvironment(env);
		this.migration = new MigrationEnvironment(env);
	}
	
	public FileEnvironment file() {
		return file;
	}
	
	public ImageEnvironment image() {
		return image;
	}
	
	public MigrationEnvironment migration() {
		return migration;
	}
	
}
