package com.lgu.abc.core.support.security.crypto;

import com.lgu.abc.core.support.security.crypto.exception.DecryptionFailureException;
import com.lgu.abc.core.support.security.crypto.exception.EncryptionFailureException;
 
public interface DataEncryptor {
	
	String encrypt(String text) throws EncryptionFailureException;
	
	String decrypt(String text) throws DecryptionFailureException;
	
}
