package com.lgu.abc.core.support.security.crypto.exception;
 
@SuppressWarnings("serial")
public class DecryptionFailureException extends CryptoException {

	public DecryptionFailureException() {}
	
	public DecryptionFailureException(String message) {
		super(message);
	}
	
	public DecryptionFailureException(Throwable e) {
		super(e);
	}
}
