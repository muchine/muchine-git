package com.lgu.abc.core.support.field.accessor;

import java.lang.reflect.Field;

public interface FieldAccessor {

	public Object getValue(Field field, Object object) throws FieldAccessException;
	
}
