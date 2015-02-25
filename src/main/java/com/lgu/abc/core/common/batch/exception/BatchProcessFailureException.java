package com.lgu.abc.core.common.batch.exception;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
public class BatchProcessFailureException extends CoreException {

	public BatchProcessFailureException(Throwable e) {
		super(e);
	}
	
}
