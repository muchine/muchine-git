package com.lgu.abc.core.support.security.crypto.key;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JCEKeyStore implements AESKeyStore {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String ALIAS = "jceksaes";

	private final String storeLocation;
	private final String storePassword;
	private final String keyPassword;
	
	@Autowired
	private JCEKeyStore(KeyStoreFactory factory) {
		this(System.getenv("ABC_KEYSTORE_LOCATION"), System.getenv("ABC_KEYSTORE_PASSWORD"), System.getenv("ABC_KEY_PASSWORD"));
		factory.add(this);
	}

	private JCEKeyStore(String storeLocation, String storePassword, String keyPassword) {
		this.storeLocation = storeLocation;
		this.storePassword = storePassword;
		this.keyPassword = keyPassword;
	}

	@Override
	public Key getKey() {
		try {
			logger.info("key store location: " + storeLocation);
			
			InputStream stream = new FileInputStream(storeLocation);
			KeyStore store = KeyStore.getInstance("JCEKS");
			store.load(stream, storePassword.toCharArray());

			return store.getKey(ALIAS, keyPassword.toCharArray());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public int order() {
		return 2;
	}

}
