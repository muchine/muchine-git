package com.lgu.abc.core.support.security.crypto.exception;
 
@SuppressWarnings("serial")
public class EncryptionFailureException extends CryptoException {

	public EncryptionFailureException() {}
	
	public EncryptionFailureException(String message) {
		super(message);
	}
	
	public EncryptionFailureException(Throwable e) {
		super(e);
	}
}
