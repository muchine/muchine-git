package com.lgu.abc.core.support.security;

import java.security.Key;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.codec.Base64;

/**
 * Desc : 암호화 Util
 * FileName : CryptoUtil.java
 * ClassName : CryptoUtil
 * Date : 2013. 8. 1.
 * Author : leechanwoo
 */
public class CipherUtils {
	
	protected static final Log logger = LogFactory.getLog(CipherUtils.class);
		
	public static String encryptHmacSHA1(String key, String message) throws RuntimeException {
		String result = null;
		try {
			Key signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			result = new String(Base64.encode(rawHmac), "UTF-8");
		} catch(Exception e) {
			throw new RuntimeException();
		}
		
		return result;
	}
}
