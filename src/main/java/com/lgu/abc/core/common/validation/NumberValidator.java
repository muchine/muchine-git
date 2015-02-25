package com.lgu.abc.core.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class NumberValidator {

	private static final Pattern pattern = Pattern.compile("^[0-9]*$");
	
	public static boolean isValid(String value, int length) {
		Matcher matcher = pattern.matcher(value);
		return matcher.matches() && value.length() <= length;
	}
	
	public static String[] parse(String value, String delimiter, int tokenCount, int tokenMaxLength) {
		if (isEmptyValue(value, delimiter)) return null;
		
		String[] tokens = value.trim().split(delimiter);
		if (tokens.length != tokenCount) 
			throw new IllegalArgumentException("Token count must be " + tokenCount + ", but " + tokens.length);
		
		for (String token : tokens) {
			if (!isValid(token, tokenMaxLength)) throw new IllegalArgumentException("Invalid token: " + token);
		}
		
		return tokens;
	}
	
	public static boolean isEmptyValue(String value, String delimiter) {
		return StringUtils.isEmpty(value) || !value.contains(delimiter);
	}
	
}
