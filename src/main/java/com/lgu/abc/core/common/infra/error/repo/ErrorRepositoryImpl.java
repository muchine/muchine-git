package com.lgu.abc.core.common.infra.error.repo;

import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.BaseMongoRepository;
import com.lgu.abc.core.common.infra.error.Error;

@SuppressWarnings("unchecked")
@Repository
public class ErrorRepositoryImpl extends BaseMongoRepository<Error, String> implements ErrorRepository {

	public ErrorRepositoryImpl() {
		super(Error.class);
	}

}
