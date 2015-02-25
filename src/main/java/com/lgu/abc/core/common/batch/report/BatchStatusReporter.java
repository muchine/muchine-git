package com.lgu.abc.core.common.batch.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.batch.AbstractBatchProcessor;
import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.support.BatchLogFinder;
import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.transport.TemplateUtils;
import com.lgu.abc.core.transport.mail.MailAgent;

@Component
public class BatchStatusReporter extends AbstractBatchProcessor {
	
	private static final String TEMPLATE_REPORT_FILE = "batch-report.html";
	private static final String TEMPLATE_RESULT_FILE = "batch-result.html";
	
	private final BatchLogFinder finder;
	
	private final MailAgent agent;	
	
	@Autowired
	BatchStatusReporter(BatchLogFinder finder, MailAgent agent, AbcConfig configuration) {
		this(finder, agent);
	}
	
	BatchStatusReporter(BatchLogFinder finder, MailAgent agent) {
		this.finder = finder;
		this.agent = agent;
	}
	
	@Scheduled(cron="0 0 6 * * *")
	@Override
	public void doTask() {
		super.doTask();
	}

	@Override
	public void process() {
		Iterable<BatchLog> logs = finder.findTodayLogs();
		if (IterableUtils.isEmpty(logs)) return;
		
		String report = createReport(logs);
		agent.send("일일 배치 현황 보고", report);
	}
	
	private String createReport(Iterable<BatchLog> logs) {
		String template = TemplateUtils.getTemplate(getClass(), TEMPLATE_REPORT_FILE);
		
		String content = createReportContent(logs);
		return template.replace("${content}", content);
	}
	
	private String createReportContent(Iterable<BatchLog> logs) {
		String template = TemplateUtils.getTemplate(getClass(), TEMPLATE_RESULT_FILE);
		
		StringBuilder builder = new StringBuilder();
		for (BatchLog log : logs) {
			builder.append(createResult(log, template));
		}
		
		return builder.toString();
	}
	
	private String createResult(BatchLog log, String template) {
		return template
				.replace("${processId}", log.getProcessId())
				.replace("${started}", log.getStarted().toString())
				.replace("${terminated}", log.getTerminated().toString())
				.replace("${elapsed}", String.valueOf(log.getElapsed()))
				.replace("${processResult}", log.getProcessResult().toString());
	}

}
