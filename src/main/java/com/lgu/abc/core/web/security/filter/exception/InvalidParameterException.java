package com.lgu.abc.core.web.security.filter.exception;

import java.util.Locale;

import lombok.Getter;

import com.lgu.abc.core.exception.BaseException;

@SuppressWarnings("serial")
public class InvalidParameterException extends BaseException {
	
	@Getter
	private final String objectName;

	public InvalidParameterException(String message) {
		super(message);
		this.objectName = "";
	}
	
	public InvalidParameterException(String resource, Locale locale) {
		super(resource, locale);
		this.objectName = "";
	}
	
	public InvalidParameterException(String objectName, String message) {
		super(message);
		this.objectName = objectName;
	}
	
	public InvalidParameterException(Throwable t) {
		super(t);
		this.objectName = "";
	}

}
