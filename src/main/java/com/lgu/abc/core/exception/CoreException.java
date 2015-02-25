package com.lgu.abc.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import com.lgu.abc.core.support.bundle.MessageBundleManager;

@SuppressWarnings("serial")
public class CoreException extends RuntimeException {

	public static String MESSAGE_PREFIX = "exception.message.";
	
	public CoreException() {}
	
	public CoreException(Throwable throwable) {
		super(throwable);
	}
	
	public CoreException(String message) {
		super(message);
	}
	
	public String getLocalizedMessage(Locale locale) {
		return MessageBundleManager.get(getBundleKey(), locale);
	}
	
	public String getBundleKey() {
		return MESSAGE_PREFIX + getClass().getSimpleName();
	}
	
	public String getStackTraceMessage() {
		StringWriter writer = new StringWriter();
		printStackTrace(new PrintWriter(writer));
		
		return writer.toString();
	}
	
}
