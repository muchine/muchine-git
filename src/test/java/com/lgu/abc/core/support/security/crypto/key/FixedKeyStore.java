package com.lgu.abc.core.support.security.crypto.key;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FixedKeyStore implements AESKeyStore {

	public FixedKeyStore() {}
	
	@Autowired
	private FixedKeyStore(KeyStoreFactory factory) {
		factory.add(this);
	}
	
	@Override
	public Key getKey() {
		byte[] key = Base64.decodeBase64("0uRxjEN7/E6V/iEl3ac+5oyblFmhfloEsrohFI41U4I=");
		return new SecretKeySpec(key, "AES");
	}

	@Override
	public int order() {
		return 1;
	}

}
