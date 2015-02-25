package com.lgu.abc.core.base.repository;

import java.io.Serializable;

import com.u2ware.springfield.repository.EntityRepository;

public interface BaseRepository<T, ID extends Serializable> extends EntityRepository<T, ID> {
	
	T save(T entity);
	
	void deleteAll(Object query);
	
	void rearrange(T entity, int to);
	
}
