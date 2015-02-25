package com.lgu.abc.core.plugin.serivce;

import com.lgu.abc.core.base.plugin.Sortable;

public interface EntityEventListener<T> extends Sortable {

	String name();
	
	void creating(T entity);
	
	void updating(T oldEntity, T newEntity);
	
	void removing(T entity);
	
	void created(T entity);
	
	void updated(T oldEntity, T newEntity);
	
	void removed(T entity);
	
	void read(T entity);
	
	void find(Iterable<T> entities);
	
}
