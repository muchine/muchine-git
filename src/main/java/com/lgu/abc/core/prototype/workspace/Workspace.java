package com.lgu.abc.core.prototype.workspace;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.LazyLoadable;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.common.interfaces.Replicable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.workspace.domain.WorkspaceCategoryCode;
import com.lgu.abc.core.prototype.workspace.repo.WorkspaceRegistry;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Workspace extends BaseEntity implements Ownable, LazyLoadable, Replicable {

	private static final int ORDER_WORKSPACE = 2;
	
	private boolean loaded = false;
	
	private Company company;
	
	private Name name;
	
	private WorkspaceCategoryCode category;
	
	public Workspace() {}
	
	public Workspace(String id, Name name, Company company, WorkspaceCategoryCode category) {
		setId(id);
		
		this.name = name;
		this.company = company;
		this.category = category;
	}

	@Override
	public OwnerType getType() {
		return OwnerType.WORKSPACE;
	}
	
	public Company getCompany() {
		if (company != null) company.load();
		return company;
	}
	
	@JsonIgnore
	public boolean isTeamWorkspace() {
		return WorkspaceCategoryCode.TEAM.equals(category.getCode());
	}
	
	@JsonIgnore
	public boolean isProjectWorkspace() {
		return WorkspaceCategoryCode.PROJECT.equals(category.getCode());
	}
	
	@JsonIgnore
	public boolean isClubWorkspace() {
		return WorkspaceCategoryCode.CLUB.equals(category.getCode());
	}
	
	@Override
	public boolean identical(Identifiable entity) {
		if (entity instanceof Ownable) {
			Ownable ownable = (Ownable) entity;
			return ownable.getType().equals(this.getType()) && ownable.getId().equals(this.getId());
		}
		
		return super.identical(entity);
	}
	
	@Override
	public Privilege authorize(Party party) {
		logger.trace("check role of party " + party + " for resource: " + resourceId());
		return party.role().privilege(resourceId());
	}

	@Override
	public void load() {
		if (!isLoaded() && getId() != null) {
			replicate(WorkspaceRegistry.get(getId()));
			setLoaded(true);
		}
	}

	@Override
	public void replicate(Object source) {
		if (source == null) return;
		
		Workspace casted = (Workspace) source;
		setId(casted.getId());
		
		this.name = casted.getName();
		this.company = casted.getCompany();
		this.category = casted.getCategory();		
	}

	@Override
	public int order(String actorId) {
		return ORDER_WORKSPACE;
	}

}
