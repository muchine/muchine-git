package com.lgu.abc.core.support.populator;

import java.lang.reflect.Field;

import com.lgu.abc.core.support.annotation.mapper.Aggregate;
import com.lgu.abc.core.support.annotation.populator.Container;

public class PopulationSupporter {

	public static boolean shouldPreserve(Field field) {
		if (nullableContainerAnnotated(field)) return true;
		
		Aggregate annotation = field.getAnnotation(Aggregate.class);
		return annotation != null && annotation.value().preserve();
	}
	
	public static boolean nullableContainerAnnotated(Field field) {
		Container annotation = field.getAnnotation(Container.class); 
		return annotation != null && annotation.nullable();
	}
	
	public static boolean containerAnnotated(Field field) {
		return field.isAnnotationPresent(Container.class);
	}
	
}
