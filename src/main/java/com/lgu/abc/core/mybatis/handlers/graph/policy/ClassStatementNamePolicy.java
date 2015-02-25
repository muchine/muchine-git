package com.lgu.abc.core.mybatis.handlers.graph.policy;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import com.lgu.abc.core.base.utils.EntityUtils;

public final class ClassStatementNamePolicy implements StatementNamePolicy {

	@Override
	public String insert(Class<?> type, String field) {
		return this.getNamespace(type, field) + ".create";
	}

	@Override
	public String update(Class<?> type, String field) {
		return this.getNamespace(type, field) + ".update";
	}

	@Override
	public String delete(Class<?> type, String field) {
		return this.getNamespace(type, field) + ".delete";
	}
	
	@Override
	public String findAll(Class<?> type, String field) {
		return this.getNamespace(type, field) + ".findAll";
	}

	@Override
	public String deleteAll(Class<?> type, String field) {
		return this.getNamespace(type, field) + ".deleteAll";
	}
	
	@Override
	public String getNamespace(Class<?> parent, String fieldName) {
		try {
			Field field = EntityUtils.getActualClass(parent).getDeclaredField(fieldName);
			
			ParameterizedType type = (ParameterizedType) field.getGenericType();
			Class<?> fieldClass = (Class<?>) type.getActualTypeArguments()[0];
			
			return fieldClass.getCanonicalName();
		} 
		catch (NoSuchFieldException e) {
			throw new IllegalArgumentException(e);
		} 
		catch (SecurityException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
