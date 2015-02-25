package com.lgu.abc.core.base.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.operation.Action;
import com.lgu.abc.core.common.interfaces.Nullable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.annotation.mapper.NoAggregate;

/**
 * The first ancestor of all domain objects.
 * 
 * @author sejoon
 *
 */
@ToString(callSuper = true, includeFieldNames = true, exclude={"actor"}) @EqualsAndHashCode(callSuper = true)
public @Data abstract class BaseEntity extends IdentifiableEntity implements Nullable {

	@JsonIgnore
	private Action action;
	
	/*
	 * CAUTION User class inherits BaseEntity circular dependency occurs here. Do we need to make User not inheriting BaseEntity?
	 */
	@JsonIgnore @NoAggregate
	private User actor;
	
	public BaseEntity() {}
	
	public BaseEntity(BaseEntity e) {
		super(e);
		writeBaseProperty(e);
	}
	
	public final void writeBaseProperty(BaseEntity entity) {
		this.actor = entity.actor;
		this.action = entity.action;
	}

	@Override
	@JsonIgnore
	public boolean isNull() {
		return getId() == null;
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		// If needed, override this in subclasses
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Initializes object's status. The reason why this method is required is that Spring may bind request parameters to the object via field directly
	 * not using setter method. Therefore, if initialization is needed, child objects should override this method. Abstract base controller should call 
	 * this after model has been bound by Spring.
	 */
	public void initialize() {
		return;
	}

}
