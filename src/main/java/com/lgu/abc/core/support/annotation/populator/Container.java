package com.lgu.abc.core.support.annotation.populator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The field that has this annotation indicates the parent of current entity.
 * Therefore, a process that applies to child objects, for example populating
 * child entities, is nothing to do with this field.
 * 
 * @author sejoon
 * 
 */
@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Inherited
@Documented
public @interface Container {
		
	/*
	 * True if entity has multiple containers
	 */
	boolean collection() default true;
	
	/*
	 * True if entity allows null container
	 */
	boolean nullable() default false;
	
}
