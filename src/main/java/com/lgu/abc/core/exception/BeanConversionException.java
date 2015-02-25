package com.lgu.abc.core.exception;

import org.springframework.core.convert.ConversionException;

@SuppressWarnings("serial")
public class BeanConversionException extends ConversionException {

	public BeanConversionException(String message) {
		super(message);
	}
	
	public BeanConversionException(String message, Throwable cause) {
		super(message, cause);
	}

}
