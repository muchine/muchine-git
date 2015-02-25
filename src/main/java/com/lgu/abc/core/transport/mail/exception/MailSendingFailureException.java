package com.lgu.abc.core.transport.mail.exception;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
public class MailSendingFailureException extends CoreException {

	public MailSendingFailureException(Throwable e) {
		super(e);
	}
	
}
