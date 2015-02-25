package com.lgu.abc.core.web.security.filter.support;

import java.util.Collection;
import java.util.Locale;

import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public class FilterValidate {

	public static void notNull(Object object, String objectName) throws InvalidParameterException {
		if (object == null) throw new InvalidParameterException(objectName + " is null.");
	}
		
	public static void one(Collection<?> object, String objectName) throws InvalidParameterException {
		notNull(object, objectName);
		
		if (object.size() != 1) throw new InvalidParameterException("invalid " + objectName + " size: " + object.size());
	}
	
	public static void isTrue(boolean expression, String message) throws InvalidParameterException {
		if (!expression) throw new InvalidParameterException(message);
	}
	
	public static void isTrue(boolean expression, String resource, Locale locale) throws InvalidParameterException {
		if (!expression) throw new InvalidParameterException(resource, locale);
	}
	
}
