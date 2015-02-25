package com.lgu.abc.core.plugin.serivce;

import java.util.List;

public abstract class AbstractServiceValidator<T> extends AbstractDomainEventListener<T> {

	public AbstractServiceValidator(EventListenerRegistry<T> registry) {
		super(registry);
	}

	@Override
	public int order() {
		return 5;
	}

	@Override
	public final void created(List<T> entities) {

	}

	@Override
	public final void updated(List<T> entities) {

	}

	@Override
	public final void created(T entity) {

	}

	@Override
	public final void updated(T oldEntity, T newEntity) {

	}

	@Override
	public final void removed(T entity) {

	}
	
}
