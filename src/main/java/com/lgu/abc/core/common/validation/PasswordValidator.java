package com.lgu.abc.core.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final int MIN_LENGTH = 8;
	
	private static final String[] PASSWORD_PATTERNS = new String[] {
		 "^(?=.*[A-Z]).*$"
		,"^(?=.*[a-z]).*$"
		,"^(?=.*[0-9]).*$"
		,"^(?=.*[~`!@#$%^&*\\(\\)\\_\\-+=\\{\\[\\}\\]|\\\\:;\\\"\\'<,>.?/]).*$"
	};
	
	private static final List<Pattern> patterns = new ArrayList<Pattern>();
	
	static {
		for (String pattern : PASSWORD_PATTERNS)
			patterns.add(Pattern.compile(pattern));
	}
	
	@Override
	public void initialize(Password constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() < MIN_LENGTH) return false;
				
		int count = 0;
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(value);
			if (matcher.matches()) count++;
		}
		
		logger.debug("matched count: " + count);
		return count >= 3;
	}	
	
}
