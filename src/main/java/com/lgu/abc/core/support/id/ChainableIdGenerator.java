package com.lgu.abc.core.support.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ChainableIdGenerator implements IdGenerator {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private IdGenerator next;
	
	/*
	 * Sequence Monitor can track how sequence increases. Can be used mainly for
	 * test purpose. However be careful because controller is running in
	 * multi-thread environment.
	 */
	private IdMonitor monitor;
		
	public ChainableIdGenerator setNext(ChainableIdGenerator next) {
		this.next = next;
		return next;
	}
	
	@Override
	public final String generateId(Object entity) {
		if (!canSupport(entity)) {
			logger.trace("can't support entity id generation.");
			return next == null ? null : next.generateId(entity);
		}			
		
		String id = generate(entity);
		if (monitor != null) monitor.add(entity.getClass(), id);
		
		return id;
	}
	
	@Override
	public final void setMonitor(IdMonitor monitor) {
		this.monitor = monitor;
		if (next != null) next.setMonitor(monitor);
	}
	
	protected abstract String generate(Object entity);
	
	protected abstract boolean canSupport(Object entity);

}
