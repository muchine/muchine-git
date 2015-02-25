package com.lgu.abc.core.base.service;

import java.util.List;

import com.u2ware.springfield.service.EntityService;

/**
 * Handles multiple creation, update and deletion requests.
 * 
 * @author sejoon
 *
 * @param <T>
 * @param <Q>
 */
public interface DomainService<T, Q> extends EntityService<T, Q> {
	
	T read(String id);
	
	List<T> save(List<T> entities);
	
	T save(T entity);
	
	List<T> create(List<T> entities);
	
	List<T> update(List<T> entities);
	
	List<T> delete(List<T> entities);
	
	void deleteAll(Q query);
	
	Long count(Object query);

	void rearrange(T entity, int to);
	
}
