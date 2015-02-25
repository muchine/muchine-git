package com.lgu.abc.core.common.vo.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.test.common.base.BaseTest;

public class SecureTextTest extends BaseTest {

	protected final Log logger = LogFactory.getLog(getClass());
		
	@Test
	public void test() {
		SecureText text = new SecureText("abcde");
		String encrypted = text.getEncrypted();
		
		logger.debug("encrypted: " + encrypted);
		
		SecureValue newText = new SecureText();
		newText.setEncrypted(encrypted);
		
		assertThat(text, is(newText));
	}
	
}
