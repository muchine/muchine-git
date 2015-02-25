package com.lgu.abc.core.base.controller;

import java.util.List;

import com.lgu.abc.core.base.service.DomainService;

public interface MacroExecutable<T, Q> {

	List<T> execute(DomainService<T, Q> service, List<T> entities);
	
}
