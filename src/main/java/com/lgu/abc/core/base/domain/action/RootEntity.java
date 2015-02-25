package com.lgu.abc.core.base.domain.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgu.abc.core.base.domain.action.ActionResult.ResultType;
import com.lgu.abc.core.base.domain.action.security.GlobalPartyAuthorizer;
import com.lgu.abc.core.base.domain.support.OwnerFactory;
import com.lgu.abc.core.common.Owner;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Actionable;
import com.lgu.abc.core.common.interfaces.Command;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.annotation.mapper.NoAggregate;
import com.lgu.abc.core.support.bundle.MessageBundleManager;

@ToString(callSuper = true, includeFieldNames = true) @EqualsAndHashCode(callSuper = true)
public @Data abstract class RootEntity extends ActionEntity implements Protectable, Containable, Actionable, Command {
	
	@JsonIgnore @NoAggregate
	private final Owner ownable = new Owner();
	
	@JsonIgnore
	private Locale locale = Locale.KOREAN;
	
	/*
	 * Result for create, update and delete action. this field is only for json request via ajax.
	 * This is not related to domain entity so need to improve the design.
	 */
	private ActionResult result;
	
	public RootEntity() {}
	
	public RootEntity(String id) {
		Validate.notNull(id, "user id is null.");
		setId(id);
	}
	
	public RootEntity(OwnerFactory creater) {
		ownable.setCreater(creater);
	}
	
	public RootEntity(RootEntity e) {
		super(e);
		
		this.ownable.setOwnable(e.ownable);
		this.result = e.result;
	}
	
	public void setActor(User actor) {
		super.setActor(actor);
		this.locale = actor.getLocale();
	}
	
	/*
	 * CAUTION DO NOT add final statement because Mybatis can't override setter/getter methods!
	 */
	public void setOwnable(Ownable ownable) {
		if (ownable == null) return;
		this.ownable.setOwnable(ownable);
		
		logger.trace("set ownable " + ownable + " to " + this);
	}
	
	public Ownable getOwnable() {
		return ownable;
	}
	
	@JsonIgnore
	public final Owner getOwner() {
		return (Owner) getOwnable();
	}
	
	@Override
	public ResourceID resourceId() {
		return getOwnable().resourceId().concatenate(super.resourceId());
	}
	
	@Override
	public final Privilege privilege(Party party) {
		return GlobalPartyAuthorizer.privilege(this, party);
	}
	
	@Override
	public Privilege authorize(Party party) {
		return GlobalPartyAuthorizer.authorize(this, party);
	}
	
	public final void writeRootProperty(Object entity) {
		writeRootProperty(entity, false);
	}
	
	public final void writeRootProperty(Object entity, boolean overwrite) {
		writeActionProperty(entity, overwrite);
		
		RootEntity casted = (RootEntity) entity;
		
		setOwnable(casted.getOwnable());
		setResult(casted.getResult());
		setLocale(casted.getLocale());
	}
	
	@Override
	public final void prepareCreation() {
		super.prepareCreation();
		prepareOwner();
	}
	
	@Override
	public void prepareCreation(User actor) {
		super.prepareCreation(actor);
		prepareOwner();
	}

	private void prepareOwner() {
		load();
		logger.debug("preparing owner using creater: " + ownable.getCreater());
		ownable.createOwner(this);
	}
	
	public final void success() {
		result = new ActionResult(ResultType.SUCCESS, successMessage());
	}
	
	public final void success(String messageKey) {
		result = new ActionResult(ResultType.SUCCESS, MessageBundleManager.get(messageKey, getLocale()));
	}
	
	public final String successMessage() {
		if (getAction() == null) return null;
		
		String key = "action.success." + getAction().toString().toLowerCase();
		String message = MessageBundleManager.get(key + "." + getClass().getSimpleName(), getLocale());
		return StringUtils.isEmpty(message) ? MessageBundleManager.get(key, getLocale()) : message;
	}
	
	public final void fail(CoreException e) {
		Locale locale = getActor() == null ? Locale.KOREAN : getActor().getLocale();
		fail(e.getLocalizedMessage(locale));
	}
	
	public final void fail(String message) {
		result = new ActionResult(ResultType.FAILURE, message);
	}
	
	public String json() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.debug("fail to stringify as json... " + toString());
			return "";
		}
	}
	
	public Collection<Containable> containers() {
		Collection<Containable> containers = new ArrayList<Containable>();
		containers.add(getOwnable());
		
		return containers;
	}
		
	/*
	 * Command hooking methods
	 */
	@Override
	public RootEntity create() {
		// Hook this method if needs to prepare creation additionally
		return this;
	}
	
	@Override
	public RootEntity update(RootEntity retrieved) {
		// Hook this method if needs to prepare update additionally
		
//		Validate.isTrue(!retrieved.getOwnable().getId().isNull(), "ownable id is null.");
		setOwnable(retrieved.getOwnable());
		setLog(retrieved.getLog());
		
		return this;
	}
	
	@Override
	public RootEntity delete(RootEntity retrieved) {
		logger.debug("before population: " + this);
		populate(retrieved, true);
		
		logger.debug("delete command: " + this);
		return this;
	}
	
	@Override
	public RootEntity read() {
		// Hook this method if needs to prepare read additionally
		return this;
	}

}
