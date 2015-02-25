package com.lgu.abc.core.prototype.org.user.domain;

import lombok.Data;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.domain.AbstractRearrangement;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.interfaces.Rearrangeable;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.NullDepartment;

public @Data class DepartmentAssociation implements Identifiable, Rearrangeable, Comparable<DepartmentAssociation> {

	private Department department;
	
	private boolean representative;
	
	private boolean leader;
	
	@JsonIgnore
	private int order = Rearrangeable.MAX_ORDER;
	
	@JsonIgnore
	private boolean selected;
		
	public DepartmentAssociation() {}
	
	public DepartmentAssociation(Department department) {
		this(department, true, Rearrangeable.MAX_ORDER);
	}
	
	public DepartmentAssociation(Department department, boolean representative, int order) {
		Validate.notNull(department, "department is null.");
		
		this.department = department;
		this.representative = representative;
		this.order = order;
	}
	
	public DepartmentAssociation(DepartmentAssociation association) {
		this.department = association.getDepartment();
		this.representative = association.isRepresentative();
		this.leader = association.isLeader();
		this.order = association.order();
	}
		
	public Department getDepartment() {
		return department == null ? NullDepartment.instance() : department;
	}

	@Override
	public String getId() {
		return getDepartment().getId();
	}

	@Override
	public boolean identical(Identifiable object) {
		if (object == null || !(object instanceof DepartmentAssociation)) return false;
		
		return object.getId().equals(getId());
	}

	@Override
	public int order() {
		return order;
	}

	@Override
	public void order(int order) {
		this.order = order;
	}
	
	@Override
	public AbstractRearrangement rearrange(int to) {
		Validate.notNull(department, "rearranging is impossible since department is null.");
		
		int from = order();
		this.order = to;
		
		return new DepartmentAssociationRearrangement(department.getId(), from, to);
	}

	@Override
	public int compareTo(DepartmentAssociation o) {
		return this.department.compareTo(o.department);
	}
	
}
