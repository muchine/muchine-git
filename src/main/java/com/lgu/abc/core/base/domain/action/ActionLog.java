package com.lgu.abc.core.base.domain.action;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.annotation.Domain;

@Domain
public @Data final class ActionLog {
	
	@JsonIgnore
	private Date registeredTime;

	@JsonIgnore
	private String register;

	@JsonIgnore
	private Date modifiedTime;

	@JsonIgnore
	private String modifier;
	
	public ActionLog() {}
	
	public ActionLog(Date registeredTime, String register, Date modifiedTime, String modifier) {
		this.registeredTime = registeredTime;
		this.register = register;
		this.modifiedTime = modifiedTime;
		this.modifier = modifier;
	}
	
	public ActionLog(Date registeredTime, String register) {
		this.registeredTime = registeredTime;
		this.register = register;
		this.modifiedTime = registeredTime;
		this.modifier = register;
	}

	public void writeLog(ActionLog log, boolean overwrite) {
		if (overwrite || this.registeredTime == null)
			this.registeredTime = log.registeredTime;
		
		if (overwrite || this.register == null)
			this.register = log.register;
		
		if (overwrite || this.modifiedTime == null)
			this.modifiedTime = log.modifiedTime;
		
		if (overwrite || this.modifier == null)
			this.modifier = log.modifier;
	}
	
	public final void prepareCreation(User actor) {
		this.registeredTime = new Date();
		this.register = actor.getId();

		this.modifiedTime = new Date();
		this.modifier = actor.getId();		
	}
	
	public final void prepareUpdate(User actor) {
		this.modifiedTime = new Date();
		this.modifier = actor.getId();
	}
	
}
