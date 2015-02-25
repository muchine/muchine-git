package com.lgu.abc.core.common.infra.file.batch;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.common.batch.TrackableBatchProcessor;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.repo.FileRepository;

@Component
public class BatchTempFileRemover extends TrackableBatchProcessor {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private FileRepository repository;
	
	@Scheduled(cron="0 0 2 * * *")
	@Override
	public void doTask() {
		super.doTask();
	}
	
	@Override
	public void process() {
		Date now = new Date();

		removeOutdatedFiles(now);
		
		logger.debug("start removing temp files from the database...");
		repository.removeOutdatedFiles(now);
	}
	
	private void removeOutdatedFiles(Date date) {
		List<File> outdated = repository.getOutdatedFiles(date);
		if (CollectionUtils.isEmpty(outdated)) return;
		
		logger.debug("removing " + outdated.size() + " files...");
		for (File file : outdated) {
			final String fullpath = file.fullpath();
			if (new java.io.File(fullpath).delete()) logger.info("removed file on " + fullpath);
		}
	}

}
