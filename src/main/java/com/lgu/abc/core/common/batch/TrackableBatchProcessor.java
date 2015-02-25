package com.lgu.abc.core.common.batch;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.batch.exception.BatchProcessFailureException;
import com.lgu.abc.core.common.batch.log.BatchLog;
import com.lgu.abc.core.common.batch.log.service.BatchLogService;
import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.prototype.org.user.SystemUser;

public abstract class TrackableBatchProcessor implements BatchProcessible {

	@Autowired
	private AbcConfig configuration;
	
	@Autowired
	private BatchLogService service;
	
	public void doTask() {
		if (!configuration.isBatchEnabled()) return;
		launch();
	}
	
	public void launch() {
		BatchLog log = new BatchLog(id());

		try {
			log.start();
			process();
			log.terminate();
		} catch (Exception e) {
			log.terminate(new BatchProcessFailureException(e));
		} finally {
			writeLog(log);
		}
	}

	private void writeLog(BatchLog log) {
		log.prepareCreation(SystemUser.instance());
		service.create(log);
	}

	protected String id() {
		return getClass().getSimpleName();
	}

}
