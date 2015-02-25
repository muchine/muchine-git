package com.lgu.abc.core.common.domain.label.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The field that has this annotation indicates the mapped labels of current entity.
 * The entity can have no labels whereas it should have at least one container. 
 * 
 * @author sejoon
 * 
 */
@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Inherited
@Documented
public @interface Labels {

}
