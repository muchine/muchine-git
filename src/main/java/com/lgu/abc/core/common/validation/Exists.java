package com.lgu.abc.core.common.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation for validating that at least one of specified fields with the fields() method in the annotation should be not null.
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = FieldExistenceValidator.class)
@Documented
public @interface Exists {

	String message() default "{field.not.exist}";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String[] fields() default {};
    
}
