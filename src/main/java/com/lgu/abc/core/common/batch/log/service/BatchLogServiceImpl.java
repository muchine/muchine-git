package com.lgu.abc.core.common.batch.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.BatchLogQuery;
import com.lgu.abc.core.common.batch.log.repo.BatchLogRepository;

@Service
public class BatchLogServiceImpl extends DomainServiceImpl<BatchLog, BatchLogQuery> implements BatchLogService {

	@Autowired
	public BatchLogServiceImpl(BatchLogRepository repository) {
		super(repository);
	}
	
}
