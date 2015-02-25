package com.lgu.abc.core.common.batch.log.fixture;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.BatchLogQuery;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.test.support.QueryTester;
import com.lgu.abc.test.support.fixture.AbstractFixtureFactory;

@Component
public class BatchLogFixtureFactory extends AbstractFixtureFactory<BatchLog, BatchLogQuery> {

	@Override
	public BatchLog getCreated() {
		BatchLog log = new BatchLog("test process");
		
		log.start();
		log.terminate(new CoreException("test exception..."));
		
		return log;
	}

	@Override
	public BatchLog getUpdated() {
		return null;
	}

	@Override
	public List<QueryTester<BatchLogQuery>> getQuery() {
		List<QueryTester<BatchLogQuery>> testers = new ArrayList<QueryTester<BatchLogQuery>>();
		
		testers.add(new QueryTester<BatchLogQuery>(new BatchLogQuery(), true));
				
		return testers;
	}

}
