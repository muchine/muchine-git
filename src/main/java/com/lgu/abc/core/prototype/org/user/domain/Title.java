package com.lgu.abc.core.prototype.org.user.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.support.annotation.Domain;

@Domain
public @Data class Title implements Comparable<Title> {

	@JsonIgnore
	private String id;
	
	@NotNull
	private Name name;
	
	@JsonIgnore
	private int order;
	
	public Title() {}
	
	public Title(String name) {
		this(Name.create(name), 0);
	}
	
	public Title(Name name, int order) {
		this.name = name;
		this.order = order;
	}
	
	@JsonValue
	public Name getName() {
		return name;
	}

	@Override
	public int compareTo(Title o) {
		return this.order - o.order;
	}
	
	public void rearrange(int newPosition) {
		this.order = newPosition;
	}
	
}
