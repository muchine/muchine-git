package com.lgu.abc.core.plugin.serivce;

import com.lgu.abc.core.common.infra.log.Logger;

public abstract class AbstractEntityEventListener<T> implements EntityEventListener<T> {

	protected Logger logger = new Logger(this.getClass());
	
	@Override
	public void creating(T entity) {}
	
	@Override
	public void updating(T oldEntity, T newEntity) {}
	
	@Override
	public void removing(T entity) {}

	@Override
	public void created(T entity) {}
	
	@Override
	public void updated(T oldEntity, T newEntity) {}
		
	@Override
	public void removed(T entity) {}

	@Override
	public void read(T entity) {}

	@Override
	public void find(Iterable<T> entities) {}
	
	@Override
	public int order() {
		return 10;
	}
	
}
