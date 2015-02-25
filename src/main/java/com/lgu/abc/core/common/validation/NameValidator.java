package com.lgu.abc.core.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

/**
 * Validate general name of entity. This prevents caret and comma from name string for the splitting issue in all modules.
 * This should be deleted later after refactoring other module sources.
 * @author Sejoon
 *
 */
public class NameValidator implements ConstraintValidator<Name, String> {

	private static final String[] INVALID_CHARS = new String[] {",", "^"}; 
	
	@Override
	public void initialize(Name constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) return true;
		
		for (String character : INVALID_CHARS) {
			if (value.contains(character)) return false;
		}
		
		return true;
	}

}
