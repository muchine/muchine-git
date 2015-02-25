package com.lgu.abc.core.plugin.ui.feed.exception;

import org.apache.http.HttpStatus;


@SuppressWarnings("serial")
public class ContentForbiddenException extends ContentException {

	public ContentForbiddenException() {
		this("org.user.social.feed.content.forbidden");
	}
	
	public ContentForbiddenException(String bundleKey) {
		super(bundleKey, HttpStatus.SC_FORBIDDEN);
	}
	
}
