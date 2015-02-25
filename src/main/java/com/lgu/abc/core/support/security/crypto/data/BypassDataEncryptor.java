package com.lgu.abc.core.support.security.crypto.data;

import com.lgu.abc.core.support.security.crypto.DataEncryptor;
import com.lgu.abc.core.support.security.crypto.exception.DecryptionFailureException;
import com.lgu.abc.core.support.security.crypto.exception.EncryptionFailureException;

public class BypassDataEncryptor implements DataEncryptor {

	@Override
	public String encrypt(String text) throws EncryptionFailureException {
		return text;
	}

	@Override
	public String decrypt(String text) throws DecryptionFailureException {
		return text;
	}

}
