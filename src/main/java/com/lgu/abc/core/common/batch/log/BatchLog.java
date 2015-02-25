package com.lgu.abc.core.common.batch.log;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.support.id.annotation.Sequence;

@Sequence(table="batch_log", field="bat_work_key")
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class BatchLog extends RootEntity {
	
	public enum BatchResult {
		SUCCESS, FAILURE;
	}
	
	private String processId;
	
	private BatchResult processResult;
	
	private Date started;
	
	private Date terminated;
	
	private String description;
	
	public BatchLog() {}
	
	public BatchLog(String processId) {
		this.processId = processId;
	}
	
	public long getElapsed() {
		return (terminated.getTime() - started.getTime()) / 1000;
	}
	
	public void start() {
		this.started = new Date();
	}
	
	public void terminate() {
		this.terminated = new Date();
		this.processResult = BatchResult.SUCCESS;
		this.description = "Succeeded.";
	}
	
	public void terminate(CoreException e) {
		this.terminated = new Date();
		
		this.processResult = BatchResult.FAILURE;
		this.description = e.getStackTraceMessage();
	}
		
}
