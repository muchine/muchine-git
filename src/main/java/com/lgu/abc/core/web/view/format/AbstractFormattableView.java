package com.lgu.abc.core.web.view.format;

import lombok.Data;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.prototype.org.user.User;

@ToString(exclude="formattable")
public @Data abstract class AbstractFormattableView<T> {

	protected T entity;
	
	protected User actor;
	
	@JsonIgnore
	protected Formattable formattable;
	
	public AbstractFormattableView(User actor) {
		this.actor = actor;
		this.formattable = FormatterRegistry.formattable(actor);
	}
	
	public AbstractFormattableView(T entity, User actor) {
		this(actor);
		this.entity = entity;		
	}
	
}
