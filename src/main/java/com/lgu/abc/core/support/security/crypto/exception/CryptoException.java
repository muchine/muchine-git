package com.lgu.abc.core.support.security.crypto.exception;

import com.lgu.abc.core.exception.CoreException;

@SuppressWarnings("serial")
public abstract class CryptoException extends CoreException {

	protected CryptoException() {};
	
	protected CryptoException(String message) {
		super(message);
	}
	
	protected CryptoException(Throwable t) {
		super(t);
	}
	
}
