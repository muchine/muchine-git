package com.lgu.abc.core.prototype.org.user;

import java.util.List;
import java.util.Locale;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.visitor.RoleMatcher;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.NullCompany;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.domain.DepartmentAssociation;
import com.lgu.abc.core.prototype.org.user.domain.DepartmentContext;
import com.lgu.abc.core.prototype.org.user.domain.Position;
import com.lgu.abc.core.prototype.org.user.domain.ProfileImage;
import com.lgu.abc.core.prototype.org.user.domain.Title;
import com.lgu.abc.core.prototype.org.user.domain.roles.Role;
import com.lgu.abc.core.prototype.org.user.repo.UserRegistry;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.annotation.mapper.Association;
import com.lgu.abc.core.support.annotation.mapper.NoAggregate;
import com.lgu.abc.core.support.formatter.UserNameFormatter;

@Domain
@NoAggregate
@EqualsAndHashCode(callSuper=true)
public @Data class User extends Party implements Ownable {
	
	private static final int ORDER_OWNER = 1;
	private static final int ORDER_SHARED = 3;
	
	@JsonIgnore
	private DepartmentContext departmentContext = new DepartmentContext();
	
	@JsonIgnore
	private String loginId;
	
	private Position position;
	
	@Association
	private Title title;
	
	private ProfileImage photo = new ProfileImage();
	
	@JsonIgnore
	private Role role = new Role();
	
	@JsonIgnore
	private boolean active = true;
	
	private Locale locale = Locale.KOREAN;
	
	public User() {}
	
	public User(Long id) {
		this(String.valueOf(id));
	}
	
	public User(String id) {
		super(id);
		role.initializeUserRole(id);
	}
	
	public User(User user) {
		replicate(user);
	}
	
	@Override
	public void replicate(Object source) {
		if (source == null) return;
		super.replicate(source);
		
		User casted = (User) source;
		
		this.departmentContext = new DepartmentContext(casted.getDepartmentContext());
		this.loginId = casted.getLoginId();
		this.position = casted.getPosition();
		this.title = casted.getTitle();
		this.photo = casted.getPhoto();
		this.role = casted.getRole();
		this.active = casted.isActive();
		this.locale = casted.getLocale();
	}
	
	@Override
	public void setId(String id) {
		super.setId(id);
		role.initializeUserRole(id);
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId.toLowerCase();
	}
	
	public void setStopped(boolean stopped) {
		this.active = !stopped;
	}
	
	public boolean isStopped() {
		return !active;
	}
	
	@JsonIgnore
	public Name getTitleName() {
		return title == null ? null : title.getName();
	}
	
	@JsonIgnore
	public Name getPositionName() {
		return position == null ? null : position.getName();
	}
	
	public String getEmail() {
		logger.trace("user: " + this + ", department: " + getDepartment());
		return loginId + "@" + getCompany().getDomain();
	}
	
	public void addAssociation(DepartmentAssociation association) {
		departmentContext.addAssociation(association);
	}
	
	public DepartmentAssociation findAssociation(Department department) {
		return departmentContext.findAssociation(department);
	}
	
	public void removeAssociation(Department department) {
		departmentContext.removeAssociation(department);
	}
	
	@JsonIgnore
	public List<DepartmentAssociation> getAssociations() {
		return departmentContext.getAssociations();
	}
	
	public List<Department> departments() {
		return departmentContext.departments();
	}
		
	public Department getDepartment() {
		return departmentContext.getDepartment();
	}
	
	@JsonIgnore
	public Department getSelectedDepartment() {
		return departmentContext.getSelectedDepartment();
	}
	
	@JsonIgnore
	public List<Department> getSelectedDepartmentHierarchy() {
		return departmentContext.getSelectedDepartmentHierarchy();
	}
	
	@JsonIgnore
	public Company getCompany() {
		Company company = getSelectedDepartment().getCompany();
		return company == null ? new NullCompany() : company;
	}
	
	public void selectDepartment(String departmentId) {
		departmentContext.selectDepartment(departmentId);
	}
	
	public boolean belongsToDepartment(String departmentId) {
		return departmentContext.belongsToDepartment(departmentId);
	}
		
	@Override
	public Privilege privilege(Protectable protectable) {
		load();
		
		Privilege priv = protectable.privilege(this);
		return priv != null ? priv : getSelectedDepartment().privilege(protectable);
	}
	
	@Override
	public Privilege privilege(Containable containable) {
		load();
		
		Privilege priv = containable.authorize(this);
		return priv != null ? priv : getSelectedDepartment().privilege(containable);
	}
	
	@Override
	public Party upper() {
		return getSelectedDepartment();
	}
	
	@Override
	public OwnerType getType() {
		return OwnerType.USER;
	}
	
	public String getText() {
		return UserNameFormatter.instance().print(this, getLocale());
	}
		
	@Override
	public Permissible role() {
		Role role = getRole();
		role.load();
		
		return role;
	}
	
	public boolean hasRole(AbstractRole role) {
		RoleMatcher matcher = new RoleMatcher((AbstractRole) role(), role);
		return matcher.matches();
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
		return this.identical(party) ? new Privilege(AccessLevel.MANAGE) : navigateRole(party); 
	}
	
	private Privilege navigateRole(Party party) {
		logger.trace("check role for resource: " + resourceId());
		return party.role().privilege(resourceId());
	}
	
	public void initialize() {
		role.load();
	}
	
	@Override
	public void load() {
		super.load();
		
		logger.trace("load user... is loaded: " + isLoaded() + ", content: " + toString());
		if (!isLoaded() && getId() != null) {
			replicate(UserRegistry.get(getId()));
			setLoaded(true);
		}
	}
	
	public void update(User retrieved) {
		super.update(retrieved);
		
		this.loginId = retrieved.getLoginId();
		this.role = retrieved.getRole();
		this.departmentContext = retrieved.getDepartmentContext();
	}
	
	@Override
	public int order(String actorId) {		
		return actorId.equals(getId()) ? ORDER_OWNER : ORDER_SHARED;
	}
	
	@Override
	public String toString() {
		return "User[id=" + getId() + ", name=" + getName() + "]";
	}

}
