package com.lgu.abc.core.common.vo.contact.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lgu.abc.core.common.vo.security.SecureValue;

@EqualsAndHashCode(callSuper=true)
public @Data class Email extends SecureValue {

	@JsonIgnore
	private String name = "";
	
	@JsonIgnore
	private String domain = "";

	public Email() {}
	
	public Email(String value) {
		setValue(value);
	}
	
	public boolean isEmpty() {
		return StringUtils.isEmpty(name) || StringUtils.isEmpty(domain);
	}
	
	@Override
	public void setValue(String value) {
		if (!canParse(value)) return;
		
		String[] tokens = value.split("@");
		this.name = tokens[0];
		this.domain = tokens[1];
	}
	
	@Override
	public String getValue() {
		return isEmpty() ? "" : name + "@" + domain;
	}
	
	@JsonValue
	public String toString() {
		return getValue();
	}
	
	public static boolean canParse(String value) {
		if (value == null || !value.contains("@")) return false;
		
		String[] tokens = value.split("@");
		return tokens != null && tokens.length == 2;
	}
	
}
