package com.lgu.abc.core.support.security.crypto.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.support.security.crypto.PasswordEncryptor;

public class PasswordMatcher {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String plain;
	
	private final String encrypted;
	
	public PasswordMatcher(String plain, String encrypted) {
		this.plain = plain;
		this.encrypted = encrypted;
	}
	
	public boolean match(PasswordEncryptor encryptor) {
		String result = encryptor.encrypt(plain);
		if (!encrypted.equals(result)) {
			logger.debug("result is not matched: " + result);
			return false;
		}
		
		return true;
	}
	
}
