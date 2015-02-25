package com.lgu.abc.core.base.domain.action;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.operation.Action;
import com.lgu.abc.core.base.utils.EntityUtils;
import com.lgu.abc.core.common.interfaces.LazyLoadable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.populator.impl.GenericPopulator;

/**
 * Parent of general domain objects. This entity has fields for interaction with
 * database actions.
 * 
 * @author sejoon
 * 
 */
@ToString(callSuper = true, includeFieldNames = true, exclude = {"log"}) @EqualsAndHashCode(callSuper = true)
public @Data class ActionEntity extends BaseEntity implements LazyLoadable {

	@JsonIgnore
	private ActionLog log = new ActionLog();
		
	public ActionEntity() {}
	
	public ActionEntity(ActionEntity e) {
		super(e);
		writeActionProperty(e);
	}
	
	public final void writeActionProperty(Object e) {
		writeActionProperty(e, false);
	}
	
	public final void writeActionProperty(Object entity, boolean overwrite) {
		if (entity == null) return;
		Assert.isTrue(entity instanceof ActionEntity, "Entity must be instance of action entity: " + entity.getClass());
		
		ActionEntity casted = (ActionEntity) entity;
		log.writeLog(casted.getLog(), overwrite);
	}
	
	@JsonIgnore
	public final String getSequenceTable() {
		return EntityUtils.getSequenceTable(getClass());
	}
	
	@JsonIgnore
	public final String getSequenceField() {
		return EntityUtils.getSequenceField(getClass());
	}
	
	@Override
	public void load() {
		logger.trace("trigger lazy loading...");
	}
	
	/**
	 * Populate this entity from source. Set value if and only if a field is null. If a field has @Aggregate annotation, 
	 * it does not set value even though it's null because null value has meaning in case of delete-insert strategy.   
	 * 
	 * @param source
	 */
	public final synchronized void populate(Object source, boolean forced) {
		logger.debug("populate entity from: " + source);
		new GenericPopulator(forced).populate(source, this);
	}
	
	/*
	 * Preparation methods
	 */
	public void prepareCreation() {
		Validate.notNull(getActor(), "actor is null. class: " + getClass());
		
		if (getAction() == null) setAction(Action.CREATE);		
		log.prepareCreation(getActor());
	}

	public void prepareCreation(User actor) {
		setActor(actor);
		prepareCreation();
	}
	
	public void prepareUpdate() {
		Validate.notNull(getActor(), "actor is null. class: " + getClass());
		
		if (getAction() == null) setAction(Action.UPDATE);
		log.prepareUpdate(getActor());
	}
	
	public void prepareUpdate(User actor) {
		setActor(actor);
		prepareUpdate();
	}
	
}
