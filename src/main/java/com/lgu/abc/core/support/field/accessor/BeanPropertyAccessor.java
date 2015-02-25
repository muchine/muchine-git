package com.lgu.abc.core.support.field.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.lgu.abc.core.base.utils.EntityUtils;

public class BeanPropertyAccessor implements FieldAccessor {

	@Override
	public Object getValue(Field field, Object object) {
		try {
			Method method = EntityUtils.findGetter(field.getName(), object);
			if (method == null) return null;
			
			return method.invoke(object);
		}
		catch(Exception e) {
			throw new FieldAccessException(e);
		}
	}

}
