package com.lgu.abc.core.support.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Support query features including workspaces to which an actor belongs and/or items shared to an actor. For example, Calendar object has this annotation
 * because it needs to query workspaces and shared calendars.
 * 
 * @author sejoon
 * 
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface Query {
	
	Type[] value() default {};
	String table() default "";
	
	public static enum Type {
		WORKSPACE, SHARED;
	}
}
