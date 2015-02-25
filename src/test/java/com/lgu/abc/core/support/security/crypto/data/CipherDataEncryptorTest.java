package com.lgu.abc.core.support.security.crypto.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.core.support.security.crypto.key.FixedKeyStore;

public class CipherDataEncryptorTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final CipherDataEncryptor encryptor = new CipherDataEncryptor(new FixedKeyStore());
		
	@Test
	public void test() throws Exception {
		test("010-5555-9999");
	}
	
	@Test
	public void testNull() {
		test(null);
	}
	
	@Test
	public void testEmpty() {
		test("");
	}
	
	@Test
	public void testDecrypt() {
		String decrypted = encryptor.decrypt("0nLcWW1UO6ggIYuSgg9wng==");
		logger.debug("decrypted: " + decrypted);
	}
	
	private void test(String text) {
		String encrypted = encryptor.encrypt(text);
		logger.debug("encrypted: " + encrypted);
		
		String decrypted = encryptor.decrypt(encrypted);
		assertThat(decrypted, is(text));
	}
	
}
