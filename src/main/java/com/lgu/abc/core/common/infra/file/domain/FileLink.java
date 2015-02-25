package com.lgu.abc.core.common.infra.file.domain;

import java.util.Date;

import lombok.Data;

public @Data class FileLink {

	private String link;
	
	private Date expiredDate;
	
	public FileLink() {}
	
	public FileLink(String link, Date expiredDate) {
		this.link = link;
		this.expiredDate = expiredDate;
	}
	
	public boolean isExpired() {
		return expiredDate == null || new Date().compareTo(expiredDate) > 0;
	}
	
}
