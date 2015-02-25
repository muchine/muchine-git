package com.lgu.abc.core.support.security.crypto.password;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.core.support.security.crypto.PasswordEncryptor;

public class DigestPasswordEncryptorTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final PasswordEncryptor encryptor = new DigestPasswordEncryptor();
	
	@Test
	public void testEncrypt() {
		logger.debug(encryptor.encrypt("cyber!@34"));
		logger.debug(encryptor.encrypt("@aleldjfhrm4"));
	}
	
	@Test
	public void testCompare() {
		final String password = "cktpeo2013@$";
		
		final String encrypted = encryptor.encrypt(password);
		assertThat(encryptor.compare(password, encrypted), is(true));
	}
	
	@Test
	public void testMatchWithMFC() {
		List<PasswordMatcher> matchers = new ArrayList<PasswordMatcher>();
		matchers.add(new PasswordMatcher("cktpeo2013@$", "4dce189145b015d486695749ee102959a66a314cb23697573dd345207c13c7d85183d9447f2efa3e360da613c850cf4b41a352eb57a0af4aa1c90a3b7d951365"));
		matchers.add(new PasswordMatcher("tkfcltkf$G1", "416f1507d54f6b0d15615d646a16cad0b968a25a2342825d7c451bd96a3a3d9e06156774b3790acda5744ad65e408bc0b862f60e161807189bf3c92a266b0b6d"));
		matchers.add(new PasswordMatcher("LGUplus&Medialog2013", "075cf4a1d53960fda8307f4e761631438923ec0d53260008f09e66ec57a8d2d8034d59521857335ecae37bbe89ab6ae274101fd37de3273d097e758bf5ed5cb3"));
	
		for (PasswordMatcher matcher : matchers)
			assertThat(matcher.match(encryptor), is(true));	
	}
	
}
