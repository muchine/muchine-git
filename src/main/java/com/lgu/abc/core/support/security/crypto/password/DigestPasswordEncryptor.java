package com.lgu.abc.core.support.security.crypto.password;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.Validate;
import org.springframework.security.crypto.codec.Hex;

import com.lgu.abc.core.support.security.crypto.PasswordEncryptor;

public class DigestPasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(String password) {
		return DigestUtils.sha512Hex(password);
	}

	@Override
	public boolean compare(String plain, String encrypted) {
		Validate.notNull(plain, "plain password is null.");
		Validate.notNull(encrypted, "encrypted password is null.");
		
		return encrypted.equals(encrypt(plain));
	}
	
	public static void main(String[] args) {
		byte[] hashed = DigestUtils.sha512("master");
		System.out.println(Base64.encodeBase64String(hashed));
		
		System.out.println("hexa: " + new String(Hex.encode(hashed)));
	}

}
