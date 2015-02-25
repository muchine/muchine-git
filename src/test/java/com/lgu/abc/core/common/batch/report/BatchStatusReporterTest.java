package com.lgu.abc.core.common.batch.report;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lgu.abc.core.common.batch.exception.BatchProcessFailureException;
import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.support.BatchLogFinder;
import com.lgu.abc.core.common.batch.report.BatchStatusReporter;
import com.lgu.abc.core.transport.mail.MailAgent;
import com.lgu.abc.core.transport.mail.MockMailAgentFactory;

public class BatchStatusReporterTest {

	@Test
	public void testProcess() {
		BatchLogFinder finder = createMockFinder();
		MailAgent agent = MockMailAgentFactory.create();
		
		BatchStatusReporter reporter = new BatchStatusReporter(finder, agent);		
		reporter.process();
	}
	
	private BatchLogFinder createMockFinder() {
		Iterable<BatchLog> logs = createLogs();
		
		BatchLogFinder finder = mock(BatchLogFinder.class);
		when(finder.findTodayLogs()).thenReturn(logs);
		
		return finder;
	}
	
	private Iterable<BatchLog> createLogs() {
		List<BatchLog> logs = new ArrayList<BatchLog>();
		
		logs.add(createLog("10", false));
		logs.add(createLog("20", true));
		
		return logs;
	}
	
	private BatchLog createLog(String processId, boolean error) {
		BatchLog log = new BatchLog(processId);
		log.start();
		
		if (error) {
			log.terminate(new BatchProcessFailureException(new RuntimeException()));
		} else {
			log.terminate();
		}
		
		return log;		
	}
	
}
