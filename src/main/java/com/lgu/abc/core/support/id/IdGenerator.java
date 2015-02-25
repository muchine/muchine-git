package com.lgu.abc.core.support.id;


/**
 * Get next and current sequence based on class type.
 * 
 * @author sejoon
 *
 */
public interface IdGenerator {
	
	String generateId(Object entity);
	
	void setMonitor(IdMonitor monitor);
	
}
