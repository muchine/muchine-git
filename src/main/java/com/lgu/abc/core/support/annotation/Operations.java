package com.lgu.abc.core.support.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lgu.abc.core.base.operation.OperationType;

/**
 * Indicates operation types a controller will support.
 * @author sejoon
 *
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Inherited
@Documented
public @interface Operations {
	OperationType[] type() default {};
}
