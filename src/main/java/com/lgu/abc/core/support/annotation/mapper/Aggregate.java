package com.lgu.abc.core.support.annotation.mapper;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;

/**
 * Indicate how object graph handler deals with this child object.
 * @author sejoon
 *
 */
@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface Aggregate {
	
	Type value() default Type.DELETE_INSERT;
	
}
