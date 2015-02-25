package com.lgu.abc.core.common.batch.log;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.BatchLogQuery;
import com.lgu.abc.core.common.batch.log.fixture.BatchLogFixtureFactory;
import com.lgu.abc.core.common.batch.log.service.BatchLogService;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.test.common.base.service.BaseServiceTest;
import com.u2ware.springfield.service.EntityService;

@Operations(type={
	OperationType.CREATE,
	OperationType.FIND_FORM
})
public class BatchLogServiceTest extends BaseServiceTest<BatchLog, BatchLogQuery> {

	@Autowired
	private BatchLogService service;
	
	@Autowired
	private BatchLogFixtureFactory factory;
	
	@Override
	protected EntityService<BatchLog, BatchLogQuery> initService() {
		setFixtureFactory(factory);
		return service;
	}
	
}
