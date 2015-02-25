package com.lgu.abc.core.common;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.support.OwnerFactory;
import com.lgu.abc.core.base.domain.support.UserOwnerFactory;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.LazyLoadable;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.annotation.populator.Replace;

@Domain
public @Data class Owner implements Ownable, LazyLoadable {
	
	@JsonIgnore @Transient
	protected final Log logger = LogFactory.getLog(getClass());
	
	@JsonUnwrapped @Replace
	private Ownable ownable = new NullOwnable();
	
	@JsonIgnore @Transient
	private OwnerFactory creater = UserOwnerFactory.create();
	
	public Owner() {}
	
	public Owner(Ownable ownable) {
		setOwnable(ownable);
	}
	
	public void createOwner(RootEntity entity) {
		if (ownable.getId() != null) return;

		ownable = creater.createOwner(entity);
		logger.trace("set owner: " + toString());
	}
	
	@Override
	public String getId() {
		return ownable.getId();
	}

	@Override
	public boolean identical(Identifiable object) {
		if (object instanceof Owner) return ownable.identical(((Owner) object).ownable);
		return ownable.identical(object);
	}
	
	@Override
	public ResourceID resourceId() {
		return ownable.resourceId();
	}
	
	@Override
	public Privilege authorize(Party party) {
		return ownable.authorize(party);
	}

	@Override
	public OwnerType getType() {
		return ownable.getType();
	}
	
	@Override
	public Name getName() {
		return ownable.getName();
	}
	
	public void setOwnable(Ownable ownable) {
		if (ownable instanceof Owner)
			this.ownable = ((Owner) ownable).ownable;
		else
			this.ownable = ownable;
		
		load();
	}
	
	/*
	 * Setters for Mybatis
	 */
	public void setUser(User user) {
		this.ownable = user;
		load();
	}
	
	public void setWorkspace(Workspace workspace) {
		this.ownable = workspace;
		load();
	}
	
	public void setCompany(Company company) {
		this.ownable = company;
		load();
	}
	
	@JsonIgnore
	public User getUser() {
		return getOwnable() instanceof User ? (User) ownable : null;
	}
	
	@JsonIgnore
	public Company getCompany() {
		return getOwnable() instanceof Company ? (Company) ownable : null;
	}
	
	@JsonIgnore
	public Workspace getWorkspace() {
		return getOwnable() instanceof Workspace ? (Workspace) ownable : null;
	}

	@Override
	public String toString() {
		return "Owner[" + ownable.getType() + ": " + ownable.getId() + "(" + ownable.getName() + ")]";
	}

	@Override
	public void load() {
		if (ownable == null) return;
		
		logger.trace("load ownable... " + ownable);
		if (ownable instanceof LazyLoadable) ((LazyLoadable) ownable).load();
	}

	@Override
	public int order(String actorId) {
		return ownable.order(actorId);
	}
	
}
