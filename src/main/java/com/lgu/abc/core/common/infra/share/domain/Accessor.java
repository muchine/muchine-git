package com.lgu.abc.core.common.infra.share.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.LazyLoadable;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.annotation.Domain;

@Domain
@ToString(exclude={"logger"})
public @Data class Accessor implements LazyLoadable, Identifiable {
	
	@JsonIgnore
	protected final Log logger = LogFactory.getLog(getClass());
	
	@JsonIgnore
	private Party party;
	
	@NotNull
	private Privilege privilege;
	
	@NotNull
	private Boolean shown;
	
	public Accessor() {}
	
	public Accessor(Party party, Privilege privilege) {
		this.party = party;
		this.privilege = privilege;
		this.shown = true;
	}
	
	public Accessor(Party party, Privilege privilege, Boolean shown) {
		this.party = party;
		this.privilege = privilege;
		this.shown = shown;
	}
	
	public boolean hasParty(Party party) {
		Validate.notNull(party, "party is null.");
		Assert.isTrue(!party.isNull(), "party id is null.");
		
		logger.trace("this party: " + getParty());
		
		if (!party.getClass().isAssignableFrom(getParty().getClass())) return false;
		return party.identical(getParty());
	}
	
	/*
	 * Setters
	 */
	public void setUser(User user) {
		this.party = user;
	}
	
	public void setDepartment(Department department) {
		this.party = department;
	}
	
	public void setCompany(Company company) {
		this.party = company;
	}
	
	/*
	 * Getters
	 */
	public User getUser() {
		return isPartyMatched(User.class) ? (User) getParty() : null; 
	}
	
	public Department getDepartment() {
		return isPartyMatched(Department.class) ? (Department) getParty() : null;
	}
	
	public Company getCompany() {
		return isPartyMatched(Company.class) ? (Company) getParty() : null;
	}
	
	public Party getParty() {
		load();
		return party;
	}
	
	public String getPartyType() {
		if (isPartyMatched(User.class)) return "user";
		if (isPartyMatched(Department.class)) return "dept";
		if (isPartyMatched(Company.class)) return "corp";
		
		return "";
	}
	
	/*
	 * I wanted to use getPrivilege instead of this method but the class acts in a strange way. It's probably because of lazy loading.
	 * Therefore, use this method to get privilege in the domain logic until the problem above can be solved.
	 */
	public Privilege privilege() {
		if (getShown() != null && getShown())
			return privilege;
		else
			return new Privilege(AccessLevel.NONE);
	}
	
	/*
	 * Helpers
	 */
	private boolean isPartyMatched(Class<?> type) {
		return getParty() != null && (type.isAssignableFrom(getParty().getClass()));
	}

	@Override
	public void load() {
		if (party != null) party.load();
	}

	@Override
	public String getId() {
		return getParty() == null ? null : getParty().getId();
	}

	@Override
	public boolean identical(Identifiable object) {
		return getId().equals(object.getId());
	}

}
