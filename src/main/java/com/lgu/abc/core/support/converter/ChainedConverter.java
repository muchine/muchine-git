package com.lgu.abc.core.support.converter;

public interface ChainedConverter<T, B> {

	void beanize(T entity, B bean);
	
	void materialize(T entity, B bean);
	
}
