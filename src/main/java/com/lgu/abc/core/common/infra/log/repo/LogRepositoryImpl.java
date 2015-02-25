package com.lgu.abc.core.common.infra.log.repo;

import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.BaseMongoRepository;
import com.lgu.abc.core.common.infra.log.Log;

@SuppressWarnings("unchecked")
@Repository
public class LogRepositoryImpl extends BaseMongoRepository<Log, String> implements LogRepository {

	public LogRepositoryImpl() {
		super(Log.class);
	}
	
}
