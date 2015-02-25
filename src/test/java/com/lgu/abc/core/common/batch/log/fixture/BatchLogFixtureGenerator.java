package com.lgu.abc.core.common.batch.log.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.BatchLogQuery;
import com.lgu.abc.core.common.batch.log.service.BatchLogService;
import com.lgu.abc.test.support.fixture.DefaultFixtureGenerator;

@Component
public class BatchLogFixtureGenerator extends DefaultFixtureGenerator<BatchLog, BatchLogQuery> {

	@Autowired
	protected BatchLogFixtureGenerator(BatchLogFixtureFactory factory, BatchLogService service) {
		super(factory, service);
	}

}
