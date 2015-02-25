package com.lgu.abc.core.support.security.crypto.exception;
 
@SuppressWarnings("serial")
public class HashFailureException extends CryptoException {
	
	public HashFailureException() {}
	
	public HashFailureException(String message) {
		super(message);
	}
	
	public HashFailureException(Throwable e) {
		super(e);
	}
}
