package com.lgu.abc.core.common.batch.log.support;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.fixture.BatchLogFixtureGenerator;
import com.lgu.abc.core.common.batch.log.support.BatchLogFinder;
import com.lgu.abc.test.common.base.BaseTest;

public class BatchLogFinderTest extends BaseTest {

	@Autowired
	private BatchLogFixtureGenerator generator;
	
	@Autowired
	private BatchLogFinder finder;
	
	@Test
	public void testFindTodayLogs() {
		// Given
		generator.generate();
		
		// When
		Iterable<BatchLog> logs = finder.findTodayLogs();
		
		// Then
		assertThat(logs, is(notNullValue()));
		for (BatchLog log : logs) {
			logger.debug("log: " + log);
		}
	}
	
}
