package com.lgu.abc.core.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.util.StringUtils;

public class FieldExistenceValidator implements ConstraintValidator<Exists, Object> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String[] fields;
	
	@Override
	public void initialize(Exists constraintAnnotation) {
		this.fields = constraintAnnotation.fields();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			for (String field : fields) {
				final String property = BeanUtils.getProperty(value, field);
				if (!StringUtils.isEmpty(property)) return true;
			}
		} 
		catch (Exception e) {
			logger.error("exception occurs during field existence validation. message: " + e);
		}
		
		return false;
	}
	
}
