package com.lgu.abc.core.plugin.serivce;

public interface EventListenerRegistry<T> {

	void addEventListener(DomainEventListener<T> listener);
	
	void removeEventListener(DomainEventListener<T> listener);
	
	boolean hasEventListener(DomainEventListener<T> listener);
	
}
