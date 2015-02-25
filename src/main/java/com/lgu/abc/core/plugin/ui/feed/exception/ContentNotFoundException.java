package com.lgu.abc.core.plugin.ui.feed.exception;

import org.apache.http.HttpStatus;

@SuppressWarnings("serial")
public class ContentNotFoundException extends ContentException {

	public ContentNotFoundException() {
		this("org.user.social.feed.content.nodata");
	}
	
	public ContentNotFoundException(String bundleKey) {
		super(bundleKey, HttpStatus.SC_NOT_FOUND);
	}
	
}
