package com.lgu.abc.core.support.security.crypto;
 
public interface PasswordEncryptor {
	
	String encrypt(String text);
	
	boolean compare(String plain, String encrypted);
	
}
