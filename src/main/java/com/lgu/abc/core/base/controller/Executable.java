package com.lgu.abc.core.base.controller;

import com.u2ware.springfield.service.EntityService;

public interface Executable<T, Q> {

	T execute(EntityService<T, Q> service, T entity);
	
}
