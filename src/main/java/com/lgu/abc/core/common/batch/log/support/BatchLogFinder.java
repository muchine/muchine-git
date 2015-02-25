package com.lgu.abc.core.common.batch.log.support;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.BatchLogQuery;
import com.lgu.abc.core.common.batch.log.service.BatchLogService;
import com.lgu.abc.core.common.vo.time.day.Day;

@Component
public class BatchLogFinder {

	@Autowired
	private BatchLogService service;
	
	public Iterable<BatchLog> findTodayLogs() {
		BatchLogQuery query = new BatchLogQuery();
		query.setStarted(new Day(new Date()).toDate());
		
		return service.find(query, null);
	}
	
}
