package com.lgu.abc.core.common.infra.file;

import org.apache.commons.lang.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.support.annotation.Domain;

@Domain
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Image extends File {
	
	private String url;
	
	public Image() {}
	
	public Image(File file) {
		super(file);
	}
	
	public Image(File file, String url) {
		super(file);
		this.url = url;
	}
	
	public Image(String id) {
		super(id);
	}
	
	public String getUrl() {
		if (!StringUtils.isEmpty(url)) return url;
		
		if (isNull()) return "";
		
		AbcConfig configuration = AbcConfig.instance();
		return configuration.upload().image().url().permanent() + getProperty().getPath();
	}
	
	@Override
	public String location() {
		AbcConfig configuration = AbcConfig.instance();
		return configuration.upload().image().location().permanent();
	}
		
}
