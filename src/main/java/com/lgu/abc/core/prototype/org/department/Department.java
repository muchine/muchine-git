package com.lgu.abc.core.prototype.org.department;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.repo.DepartmentRegistry;
import com.lgu.abc.core.prototype.org.department.support.DepartmentHierarchyFormatter;

@ToString(callSuper=true, includeFieldNames=true, exclude="virtual") @EqualsAndHashCode(callSuper=true)
public @Data class Department extends Party implements Comparable<Department> {
	
	public static final String NULL_DEPARTMENT = "0";
	
	public static final int NULL_ORDER = -1;
	
	private Company company;
	
	@JsonIgnore
	private Department parent;
	
	@JsonIgnore
	private boolean virtual;
	
	@JsonIgnore
	private int order;
	
	public Department() {}
	
	public Department(String id) {
		super(id);
	}
	
	public Department(String id, Company company, Name name) {
		super(id, name);
		this.company = company;
	}
	
	public Department(Department department) {
		replicate(department);
	}
	
	@Override
	public boolean isNull() {
		return super.isNull() || NULL_DEPARTMENT.equals(getId());
	}

	public Department parent() {
		logger.trace("get parent: " + parent);
		if (parent == null || parent.isNull()) return NullDepartment.instance();
		
		Validate.isTrue(!this.identical(parent), "parent is identical with this object.");
		
		parent.load();
		return parent;
	}
	
	public List<Department> hierarchy() {
		List<Department> hierarchy = new ArrayList<Department>();
		hierarchy.add(this);
		for (Department d = parent(); !d.isNull(); d = d.parent) {
			hierarchy.add(d);
		}
		
		return hierarchy;
	}
	
	@JsonIgnore
	public String getBreadcrum() {
		return DepartmentHierarchyFormatter.instance().breadcrum(this);
	}
	
	public boolean hasAncestor(Department department) {
		if (parent().isNull()) return false;
		return parent().identical(department) ||  parent().hasAncestor(department);
	}
	
	public int depth() {
		return parent().isNull() ? 1 : parent().depth() + 1;
	}

	@Override
	public void replicate(Object source) {
		if (source == null) return;
		
		super.replicate(source);
		
		Department casted = (Department) source;
		this.company = casted.getCompany();
		this.parent = casted.parent();
		this.virtual = casted.isVirtual();
		this.order = casted.getOrder();
	}

	@Override
	public Privilege privilege(Protectable protectable) {
		Privilege priv = protectable.privilege(this);
		if (priv != null) return priv;
		
		if (!parent().isNull()) return parent().privilege(protectable);
		
		return getCompany().privilege(protectable);
	}
	
	@Override
	public Privilege privilege(Containable containable) {
		Privilege priv = containable.authorize(this);
		if (priv != null) return priv;
		
		if (!parent().isNull()) return parent().privilege(containable);
		
		return getCompany().privilege(containable);
	}
	
	@Override
	public Party upper() {
		Department parent = parent();
		return parent.isNull() ? getCompany() : parent;
	}
	
	@Override
	public void load() {
		super.load();
		
		if (!isLoaded() && getId() != null) {
			replicate(DepartmentRegistry.get(getId()));
			setLoaded(true);
		}
	}

	@Override
	public int compareTo(Department o) {
		if (depth() != o.depth()) return depth() - o.depth();
		return order - o.order;
	}
	
	public void rearrange(int newPosition) {
		this.order = newPosition;
	}
	
	public Department create() {
		if (parent == null || parent.isNull()) parent = NullDepartment.instance();
		this.virtual = false;
		
		return this;
	}
	
	public Department update(Department retrieved) {
		super.update(retrieved);
		
		this.company = retrieved.getCompany();
		this.virtual = retrieved.isVirtual();
		
		/*
		 * if parent has been updated, the order should be calculated newly by department rearranger
		 */
		this.order = retrieved.getOrder();
		if (parent == null || parent.isNull()) parent = NullDepartment.instance();
		
		return this;
	}
	
	public boolean hasSameParentWith(Department department) {
		return getCompany().identical(department.getCompany()) && parent().identical(department.parent());
	}
	
}
