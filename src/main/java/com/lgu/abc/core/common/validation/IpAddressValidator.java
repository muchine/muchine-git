package com.lgu.abc.core.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate IP address. Allows numbers only from 0 to 255 or '*'.
 * 
 * @author sejoonlim
 */
public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

	private static final String IP_PATTERN = 
			  "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])|\\*)\\."
			+ "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])|\\*)\\."
			+ "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])|\\*)\\."
			+ "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])|\\*)$";

	@Override
	public void initialize(IpAddress constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return false;
		
		Pattern pattern = Pattern.compile(IP_PATTERN);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
