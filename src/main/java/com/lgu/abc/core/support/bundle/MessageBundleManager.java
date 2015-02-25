package com.lgu.abc.core.support.bundle;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public class MessageBundleManager {
	
	private static MessageSource messageSource;
	
	@Autowired
	private MessageBundleManager(MessageSource source) {
		messageSource = source;
	}
	
	public static String get(String key, Locale locale) {
		try {
			return messageSource.getMessage(key, null, locale);
		}
		catch (NoSuchMessageException e) {
			return null;
		}
	}
	
	public static String get(String key, Locale locale, Object... args) {
		try {
			return messageSource.getMessage(key, args, locale);
		}
		catch (NoSuchMessageException e) {
			return null;
		}
	}	
	
}
