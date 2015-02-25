package com.lgu.abc.core.common.infra.file.domain;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.infra.file.support.icon.FileIcon;
import com.lgu.abc.core.common.infra.file.support.icon.type.FileIconFactory;
import com.lgu.abc.core.common.interfaces.Cleanable;

public @Data class FileProperty implements Cleanable {

	private String name;
	
	@JsonIgnore
	private String path;
	
	public FileProperty() {}
	
	public FileProperty(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	@JsonIgnore
	public FileIcon getIcon() {
		return FileIconFactory.icon(getExtension());
	}

	@JsonIgnore
	public String getExtension() {
		if (StringUtils.isEmpty(name)) return "";
		
		String[] tokens = name.split("\\.");
		if (tokens == null || tokens.length == 0) return "";
		
		return tokens[tokens.length - 1];		
	}
	
	@Override
	public void clean() {
		name = null;
		path = null;
	}
	
}
