package com.lgu.abc.core.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.lgu.abc.core.common.vo.time.day.AbstractDay;

public class DayValidator implements ConstraintValidator<Day, AbstractDay> {

	private static final String PATTERN ="[0-9]{8}";
	
	private static final DayValidator instance = new DayValidator(); 
	
	@Override
	public void initialize(Day constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(AbstractDay value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) return false;
		return isValid(value.getDate());
	}
	
	public boolean isValid(String date) {
		if (StringUtils.isEmpty(date)) return false;
		
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
	}
	
	public static DayValidator instance() {
		return instance;
	}

}
