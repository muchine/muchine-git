package com.lgu.abc.core.support.field.accessor;

import java.lang.reflect.Field;

public class DirectFieldAccessor implements FieldAccessor {

	@Override
	public Object getValue(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		}
		catch(IllegalAccessException e) {
			throw new IllegalStateException(e);
		}		
	}

}
