package com.lgu.abc.core.common.vo.contact.domain;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lgu.abc.core.common.validation.NumberValidator;

public @Data class ZipCode {
	
	private static final String[] EMPTY_VALUE = new String[] {"", ""};
	
	public static final String DELIMITER = "-";
	
	private String first = "";
	
	public String last = "";
	
	public ZipCode() {}
	
	public ZipCode(String zip) {
		setValue(zip);
	}
	
	public ZipCode(String first, String last) {
		setValue(first + DELIMITER + last);
	}
	
	public boolean isEmpty() {
		return StringUtils.isEmpty(first) || StringUtils.isEmpty(last);
	}
	
	public void setValue(String value) {
		String[] parsed = parse(value);
		
		this.first = parsed[0];
		this.last = parsed[1];
	}
	
	public String getValue() {
		return isEmpty() ? "" : toString();
	}
	
	@JsonValue
	public String toString() {
		return first + DELIMITER + last;
	}
	
	private String[] parse(String value) {
		String[] parsed = NumberValidator.parse(value, DELIMITER, 2, 3);
		return parsed == null ? EMPTY_VALUE : parsed;
	}
	
}
