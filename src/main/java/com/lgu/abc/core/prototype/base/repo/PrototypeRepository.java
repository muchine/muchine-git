package com.lgu.abc.core.prototype.base.repo;

import com.u2ware.springfield.repository.EntityRepository;

public interface PrototypeRepository<T> extends EntityRepository<T, String> {

	void evict(String id);
	
}
