package com.lgu.abc.core.support.converter;

public interface Converter<T, B> {

	B beanize(T entity);
	
	T materialize(B bean);
	
}
