package com.lgu.abc.core.common.infra.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.infra.log.repo.LogRepository;

@Component
public class LoggerFactory {

	/*
	 * write a log to mongodb repository if true.
	 */
	private static final String LOGGER_WRITABLE = "logger.mongodb.writable";
	
	@Autowired
	private LogRepository repository;
	
	private boolean writable;
	
	@Autowired
	public LoggerFactory(AbcConfig config) {
		writable = Boolean.parseBoolean(config.getProperty(LOGGER_WRITABLE));
	}
	
	public Logger getLog(Class<?> type) {
		return new Logger(type, repository, writable);
	}
	
}
