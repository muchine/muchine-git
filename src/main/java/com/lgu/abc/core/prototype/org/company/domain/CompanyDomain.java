package com.lgu.abc.core.prototype.org.company.domain;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.support.id.annotation.Sequence;

@Sequence(table = "usr_corp_domn", field = "domn_seq")
public @Data class CompanyDomain implements Comparable<CompanyDomain> {

	private String name;
	
	private boolean primary;
	
	@JsonIgnore
	private Date registeredTime = new Date();
	
	public CompanyDomain() {}
	
	public CompanyDomain(String name) {
		this(name, false);
	}
	
	public CompanyDomain(String name, boolean primary) {
		this.name = name;
		this.primary = primary;
	}

	@Override
	public int compareTo(CompanyDomain o) {
		return o.registeredTime.compareTo(this.registeredTime);
	}
	
}
