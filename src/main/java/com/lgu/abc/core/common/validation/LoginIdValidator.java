package com.lgu.abc.core.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginIdValidator implements ConstraintValidator<LoginId, String> {

	private static final int MIN_LENGTH = 3;
	
	private static final Pattern pattern = Pattern.compile("[a-z0-9._-]{3,20}");
	
	@Override
	public void initialize(LoginId constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() < MIN_LENGTH) return false;
		
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
