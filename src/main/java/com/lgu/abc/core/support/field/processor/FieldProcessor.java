package com.lgu.abc.core.support.field.processor;

import java.lang.reflect.Field;

public interface FieldProcessor {

	public void process(Field field, Object object) throws Exception;
	
}
