package com.lgu.abc.core.common.infra.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.common.infra.error.repo.ErrorRepository;

@Service
public class ErrorService {

	@Autowired
	private ErrorRepository repository;
	
	public Error create(Error entity) {
		return repository.create(entity);
	}

}
