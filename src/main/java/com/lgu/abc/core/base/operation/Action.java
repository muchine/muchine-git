package com.lgu.abc.core.base.operation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreType
public @Data class Action {

	public static final String ACTION_CREATE	= "CREATE";
	public static final String ACTION_UPDATE	= "UPDATE";
	public static final String ACTION_DELETE	= "DELETE";
	public static final String ACTION_FIND	= "FIND";
	
	public static final Action CREATE	= new Action(ACTION_CREATE, OperationType.CREATE);
	public static final Action UPDATE	= new Action(ACTION_UPDATE, OperationType.UPDATE);
	public static final Action DELETE	= new Action(ACTION_DELETE, OperationType.DELETE);
	public static final Action FIND	= new Action(ACTION_FIND, OperationType.FIND);	

	@Getter(AccessLevel.NONE)
	protected final String name;
	
	protected final OperationType type;
	
	protected Action(String name) {
		this(name, OperationType.UNDEFINED);
	}
	
	protected Action(String name, OperationType type) {
		this.name = name;
		this.type = type;
	}
	
	public String name() {
		return this.name;
	}
	
	public boolean isCreating() {
		return OperationType.CREATE.equals(type);
	}
	
	public boolean isUpdating() {
		return OperationType.UPDATE.equals(type);
	}
	
	public boolean isDeleting() {
		return OperationType.DELETE.equals(type);
	}
	
	public boolean isFinding() {
		return OperationType.FIND.equals(type);
	}
	
	public String toString() {
		return name();
	}
	
}
