package com.lgu.abc.core.web;

import lombok.Data;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public @Data class Url {
	
	@JsonIgnore
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private final String base;
	
	private final String path;
	
	private final String extension;
	
	public Url(String base, String path, String extension) {
		Validate.notNull(base, "base url is null.");
		Validate.notNull(path, "path is null.");
		
		this.base = base;
		this.path = path;
		this.extension = extension;
	}
	
	public String location() {
		logger.debug("extension: " + extension);
		return uri() + extension();
	}
	
	private String uri() {
		return base + path;
	}
	
	private String extension() {
		return extension == null || extension.length() == 0 ? "" : "." + extension;
	}
	
	@Override
	public String toString() {
		return base + path + extension;
	}
	
}
