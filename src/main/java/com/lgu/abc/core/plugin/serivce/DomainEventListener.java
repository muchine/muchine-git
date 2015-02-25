package com.lgu.abc.core.plugin.serivce;

import java.util.List;

public interface DomainEventListener<T> extends EntityEventListener<T> {
	
	void creating(List<T> entities);
		
	void updating(List<T> entities);
		
	void created(List<T> entities);
		
	void updated(List<T> entities);
		
}
