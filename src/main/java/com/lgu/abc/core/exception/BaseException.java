package com.lgu.abc.core.exception;

import java.util.Locale;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.support.bundle.MessageBundleManager;

@SuppressWarnings("serial")
@Data @EqualsAndHashCode(callSuper=false)
public abstract class BaseException extends Exception {
	
	private static Log logger = LogFactory.getLog(BaseException.class);
	
	private String message;
	
	public BaseException(String message) {
		super(message);
		this.message = message;
		logger.error("error: " + message);
	}
	
	public BaseException(String resource, Locale locale) {
		this(MessageBundleManager.get(resource, locale));
	}
		
	public BaseException(Throwable t) {
		super(t);
		this.message = t.getMessage();
	}
	
}
