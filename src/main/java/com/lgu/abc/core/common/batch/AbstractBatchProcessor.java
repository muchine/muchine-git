package com.lgu.abc.core.common.batch;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.configuration.AbcConfig;

public abstract class AbstractBatchProcessor implements BatchProcessible {

	@Autowired
	private AbcConfig configuration;
	
	public void doTask() {
		if (configuration.isBatchEnabled()) process();
	}
	
}
