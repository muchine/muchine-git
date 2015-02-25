package com.lgu.abc.core.base.domain.view;

public interface EntityCommand<T> {

	void create(T entity);
	
	void update(T entity);
	
	void read(T entity);
	
	void load(T entity);
	
}
