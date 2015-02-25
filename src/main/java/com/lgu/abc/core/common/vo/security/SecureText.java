package com.lgu.abc.core.common.vo.security;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang.StringUtils;

@EqualsAndHashCode(callSuper=true)
public @Data class SecureText extends SecureValue {

	private String value;
	
	public SecureText() {}
	
	public SecureText(String value) {
		this.value = value;
	}
	
	public boolean isEmpty() {
		return StringUtils.isEmpty(value);
	}
	
	@Override
	public String getValue() {
		return isEmpty() ? "" : value;
	}
	
}
