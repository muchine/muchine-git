package com.lgu.abc.core.common.vo.contact.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lgu.abc.core.common.validation.NumberValidator;
import com.lgu.abc.core.common.vo.security.SecureValue;

@EqualsAndHashCode(callSuper=true)
public @Data class PhoneNumber extends SecureValue {

	private static final String DELIMITER = "-";
	
	private static String[] EMPTY_VALUE = new String[] {"", "", ""};
	
	private String area = "";
	
	private String region = "";
	
	private String local = "";
	
	public PhoneNumber() {}
	
	public PhoneNumber(String number) {
		setValue(number);
	}
	
	@Override
	public void setValue(String value) {
		String[] tokens = parse(value);
		
		area = tokens[0];
		region = tokens[1];
		local = tokens[2];
	}
	
	@Override
	public String getValue() {
		return isEmpty() ? "" : area + DELIMITER + region + DELIMITER + local;
	}
	
	@JsonValue
	@Override
	public String toString() {
		return getValue();
	}
	
	public boolean isEmpty() {
		return StringUtils.isEmpty(area) || StringUtils.isEmpty(region) || StringUtils.isEmpty(local);
	}
			
	private String[] parse(String number) {
		String[] parsed = NumberValidator.parse(number, DELIMITER, 3, 6);
		return parsed == null ? EMPTY_VALUE : parsed;
	}
		
}
