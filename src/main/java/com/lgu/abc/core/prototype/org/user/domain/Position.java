package com.lgu.abc.core.prototype.org.user.domain;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.vo.Name;

public @Data class Position implements Identifiable, Comparable<Position> {
	
	public static final Position NULL = new Position("0", Name.create(""), 0);

	@JsonIgnore
	private String id;
	
	private Name name;
	
	@JsonIgnore
	private int order;
	
	public Position() {}
	
	public Position(String name) {
		this(Name.create(name), 0);
	}
	
	public Position(Name name, int order) {
		this(null, name, order);
	}
	
	public Position(String id, Name name, int order){
		this.id = id;
		this.name = name;
		this.order = order;
	}
	
	@JsonValue
	public Name getName() {
		return name;
	}

	@Override
	public int compareTo(Position o) {
		return this.order - o.order;
	}
	
	public void rearrange(int newPosition) {
		this.order = newPosition;
	}

	@Override
	public boolean identical(Identifiable object) {
		if (!(object instanceof Position)) return false;
		return this.getId() != null && this.getId().equals(object.getId());
	}
	
}
