package com.lgu.abc.core.common.vo.contact.domain;

import lombok.Data;

import org.thymeleaf.util.StringUtils;

public @Data class PostalAddress {

	private ZipCode zip = new ZipCode();
	
	private String main = "";
	
	private String detail = "";
	
	public PostalAddress() {}
	
	public PostalAddress(String zip, String main, String detail) {
		this.zip = StringUtils.isEmpty(zip) ? null : new ZipCode(zip);
		this.main = main;
		this.detail = detail;
	}
				
	public String getMain() {
		return main == null ? "" : main;
	}
	
	public String getDetail() {
		return detail == null ? "" : detail;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (zip != null) {
			builder.append("(").append(zip.toString()).append(") ");	
		}
		
		builder.append(getMain()).append(" ").append(getDetail());		
		return builder.toString();
	}
	
}
