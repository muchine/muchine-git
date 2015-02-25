package com.lgu.abc.core.prototype.org.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.company.domain.CompanyDomain;
import com.lgu.abc.core.prototype.org.company.repo.CompanyRegistry;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

@Domain
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Company extends Party implements Ownable {
	
	private static final int ORDER_COMPANY = 4;
	
	/*
	 * Default company domain
	 */
	private String domain;
	
	/*
	 * Access domains
	 */
	@JsonIgnore @Aggregate(Type.DELETE_INSERT)
	private List<CompanyDomain> domains;
	
	public Company() {}
	
	public Company(String id) {
		super(id);
	}
		
	public Company(String id, Name name) {
		super(id, name);
	}

	public Company(Company e) {
		replicate(e);
	}
		
	public void addAccessDomain(String domain) {
		if (domains == null) domains = new ArrayList<CompanyDomain>();
		domains.add(new CompanyDomain(domain));
	}
	
	@Override
	public void replicate(Object source) {
		if (source == null) return;
		
		super.replicate(source);
		
		Company casted = (Company) source;
		domains = casted.getDomains();
	}

	@Override
	public Privilege privilege(Protectable protectable) {
		return protectable.privilege(this);
	}
	
	@Override
	public Privilege privilege(Containable containable) {
		return containable.authorize(this);
	}
	
	@Override
	public Party upper() {
		return null;
	}
	
	@Override
	public OwnerType getType() {
		return OwnerType.COMPANY;
	}

	@Override
	public boolean identical(Identifiable entity) {
		if (entity instanceof Ownable) {
			Ownable ownable = (Ownable) entity;
			if (ownable.getType() == null || ownable.getId() == null) return false;
			return ownable.getType().equals(this.getType()) && ownable.getId().equals(this.getId());
		}
		
		return super.identical(entity);
	}

	@Override
	public Privilege authorize(Party party) {
		return this.identical(party) ? new Privilege(AccessLevel.MANAGE) : navigateRole(party); 
	}
	
	private Privilege navigateRole(Party party) {
		logger.trace("check role for resource: " + resourceId());
		return party.role().privilege(resourceId());
	}

	@Override
	public void load() {
		super.load();
		
		if (!isLoaded() && getId() != null) {
			replicate(CompanyRegistry.get(getId()));
			setLoaded(true);
		}
		
		sortDomains();
	}
	
	private void sortDomains() {
		if (CollectionUtils.isEmpty(domains)) return;
		Collections.sort(domains);
	}

	@Override
	public int order(String actorId) {
		return ORDER_COMPANY;
	}
	
}
