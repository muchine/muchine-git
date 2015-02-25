package com.lgu.abc.core.plugin.serivce;

import java.util.List;

public abstract class AbstractDomainEventListener<T> extends AbstractEntityEventListener<T> implements DomainEventListener<T> {
	
	private final String name;
	
	public AbstractDomainEventListener(EventListenerRegistry<T> registry) {
		this.name = getClass().getSimpleName();
		registry.addEventListener(this);		
	}

	@Override
	public void creating(List<T> entities) {}
	
	@Override
	public void updating(List<T> entities) {}
	
	@Override
	public void created(List<T> entities) {}
	
	@Override
	public void updated(List<T> entities) {}

	@Override
	public final String name() {
		return name;
	}
	
}
