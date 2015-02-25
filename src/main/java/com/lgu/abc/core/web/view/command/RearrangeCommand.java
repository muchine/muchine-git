package com.lgu.abc.core.web.view.command;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class RearrangeCommand extends AbstractCommand {

	@NotNull
	private String id;
	
	@NotNull @Min(0) @Max(1000)
	private Integer to;
	
	private RootEntity entity;
	
	public RearrangeCommand() {}
	
	public RearrangeCommand(String id, int to) {
		this.id = id;
		this.to = to;
	}
	
	/*
	 * since a command object doesn't know how to create entity object, controller creates and sets the entity object 
	 * to be returned from read() method.
	 */
	public void setEntity(RootEntity entity) {
		this.entity = entity;
	}

	@Override
	public RootEntity update(RootEntity retrieved) {
		return retrieved;
	}

	@Override
	public RootEntity read() {
		entity.setId(id);
		return entity;
	}
	
}
