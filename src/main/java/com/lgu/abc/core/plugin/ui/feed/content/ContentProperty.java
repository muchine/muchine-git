package com.lgu.abc.core.plugin.ui.feed.content;

import java.util.List;

import lombok.Data;

import com.lgu.abc.core.common.infra.file.File;

public @Data class ContentProperty {

	private String title;
	
	private String content;
	
	private List<File> files;
	
	public ContentProperty() {}
	
	public ContentProperty(String title, String content) {
		this(title, content, null);
	}
	
	public ContentProperty(String title, String content, List<File> files) {
		this.title = title;
		this.content = content;
		this.files = files;
	}
	
}
