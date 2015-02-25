package com.lgu.abc.core.plugin.ui.feed.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
@ToString(callSuper = true, includeFieldNames = true) @EqualsAndHashCode(callSuper = true)
public abstract @Data class ContentException extends CoreException {

	private final String bundleKey;
	
	private final int statusCode;
		
	protected ContentException(String bundleKey, int statusCode) {
		this.bundleKey = bundleKey;
		this.statusCode = statusCode;
	}
	
}
