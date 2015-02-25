package com.lgu.abc.core.support.validator;

import java.util.regex.Pattern;

public class DateTimeValidator {

	private static final String DATE_PATTERN = "[0-9]{8}";
	private static final String TIME_PATTERN = "[0-9]{6}";
	
	private static final Pattern datePattern = Pattern.compile(DATE_PATTERN);
	private static final Pattern timePattern = Pattern.compile(TIME_PATTERN);
	
	public static boolean validateDate(String date) {
		return date != null && datePattern.matcher(date).matches();
	}
	
	public static boolean validateTime(String time) {
		return time != null && timePattern.matcher(time).matches();
	}
	
}
