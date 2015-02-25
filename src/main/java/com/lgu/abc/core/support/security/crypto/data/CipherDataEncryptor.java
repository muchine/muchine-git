package com.lgu.abc.core.support.security.crypto.data;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.lgu.abc.core.support.security.crypto.DataEncryptor;
import com.lgu.abc.core.support.security.crypto.exception.DecryptionFailureException;
import com.lgu.abc.core.support.security.crypto.exception.EncryptionFailureException;
import com.lgu.abc.core.support.security.crypto.key.AESKeyStore;

public class CipherDataEncryptor implements DataEncryptor {

	private static final String IV = "cktpeormfnqdnpdj"; // 16bit
	
	private final Key key;
		
	public CipherDataEncryptor(AESKeyStore store) {
		key = store.getKey();
	}
	
	@Override
	public String encrypt(String text) throws EncryptionFailureException {
		try {
			if (StringUtils.isEmpty(text)) return text;
			
			byte[] encrypted = process(Cipher.ENCRYPT_MODE, text.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(encrypted));	
		} catch(Exception e) {
			throw new EncryptionFailureException(e);
		}
	}
	
	@Override
	public String decrypt(String text) throws DecryptionFailureException {
		try {
			if (StringUtils.isEmpty(text)) return text;
			
			byte[] decoded = Base64.decodeBase64(text.getBytes());
			byte[] decrypted = process(Cipher.DECRYPT_MODE, decoded);
			
			return new String(decrypted, "UTF-8");
		} catch(Exception e) {
			throw new DecryptionFailureException(e);
		}
	}
	
	private byte[] process(int mode, byte[] bytes) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, key, new IvParameterSpec(IV.getBytes()));
		
		return cipher.doFinal(bytes);
	}

}
