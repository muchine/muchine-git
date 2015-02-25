package com.lgu.abc.core.support.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class ChainableConverter<T, B> implements Converter<T, B> {

	private final List<ChainedConverter<T, B>> chain = new ArrayList<ChainedConverter<T, B>>(); 
	
	@Override
	public final B beanize(T entity) {
		if (entity == null) return null;
		
		B bean = bean();
		for (ChainedConverter<T, B> converter : chain) converter.beanize(entity, bean);
		
		return bean;
	}

	@Override
	public final T materialize(B bean) {
		if (bean == null) return null;
		
		T entity = entity();
		for (ChainedConverter<T, B> converter : chain) converter.materialize(entity, bean);
		
		return entity;
	}

	public ChainableConverter<T, B> add(ChainedConverter<T, B> converter) {
		chain.add(converter);
		return this;
	}
	
	protected abstract T entity();
	
	protected abstract B bean();
	
}
