package com.lgu.abc.core.support.security.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.support.security.crypto.data.CipherDataEncryptor;
import com.lgu.abc.core.support.security.crypto.key.KeyStoreFactory;
import com.lgu.abc.core.support.security.crypto.password.DigestPasswordEncryptor;

@Component
public class CryptoFactory {
	
	private static PasswordEncryptor passwordEncryptor = new DigestPasswordEncryptor();
	
	private static DataEncryptor dataEncryptor;
	
	private static KeyStoreFactory keyStoreFactory;
	
	@Autowired
	private CryptoFactory(KeyStoreFactory factory) {
		keyStoreFactory = factory;
	}
	
	public static PasswordEncryptor getPasswordEncryptor() {
		return passwordEncryptor;
	}
	
	public static DataEncryptor getDataEncryptor() {
		/*
		 * We use lazy initialization for a data encryptor in order to make sure that key store factory is completely initialized.
		 * For test purpose, a key store factory may contain a variety of key store implementation.
		 * In other classes, DO NOT call this method statically. It will not work.
		 */
		if (dataEncryptor == null) initialize();
		return dataEncryptor;
	}
	
	private static synchronized void initialize() {
		dataEncryptor = new CipherDataEncryptor(keyStoreFactory.getStore());
	}
		
}
