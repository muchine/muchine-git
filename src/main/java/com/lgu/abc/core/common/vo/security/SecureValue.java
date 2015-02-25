package com.lgu.abc.core.common.vo.security;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.lgu.abc.core.support.security.crypto.CryptoFactory;
import com.lgu.abc.core.support.security.crypto.DataEncryptor;

public abstract @Data class SecureValue {

	public String getEncrypted() {
		DataEncryptor encryptor = CryptoFactory.getDataEncryptor();
		
		String value = getValue();
		return StringUtils.isEmpty(value) ? value : encryptor.encrypt(value);
	}
	
	public void setEncrypted(String encrypted) {
		DataEncryptor encryptor = CryptoFactory.getDataEncryptor();
		
		String value = StringUtils.isEmpty(encrypted) ? encrypted : encryptor.decrypt(encrypted);
		setValue(value);
	}
	
	@Override
	public String toString() {
		return getValue();
	}
	
	protected abstract String getValue();
	
	protected abstract void setValue(String value);
	
}
