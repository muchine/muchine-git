package com.lgu.abc.core.prototype.org.user.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Data;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.NullDepartment;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

@Domain
public @Data class DepartmentContext implements Iterable<DepartmentAssociation> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Aggregate(Type.DELETE_INSERT)
	private List<DepartmentAssociation> associations;
	
	public DepartmentContext() {}
	
	public DepartmentContext(DepartmentContext context) {
		if (context.associations == null) return;
		this.associations = new ArrayList<DepartmentAssociation>(context.associations);
	}
	
	public DepartmentContext(List<DepartmentAssociation> associations) {
		this.associations = associations;
	}
	
	public boolean isEmpty() {
		return CollectionUtils.isEmpty(associations);
	}
	
	public void addAssociation(DepartmentAssociation association) {
		if (getAssociations() == null) associations = new ArrayList<DepartmentAssociation>();
		getAssociations().add(association);
	}
	
	public DepartmentAssociation findAssociation(Department department) {
		if (getAssociations() == null) return null;
		
		for (DepartmentAssociation association : getAssociations()) {
			if (department.identical(association.getDepartment())) return association;
		}
		
		return null;
	}
	
	public void removeAssociation(Department department) {
		DepartmentAssociation association = findAssociation(department);
		associations.remove(association);
	}
	
	public List<Department> departments() {
		List<Department> depts = new ArrayList<Department>();
		
		if (getAssociations() != null)
			for (DepartmentAssociation a : getAssociations()) depts.add(a.getDepartment());
		
		return depts;
	}
	
	public Department getDepartment() {
		logger.trace("associations: " + associations);
		if (getAssociations() == null || getAssociations().isEmpty()) return NullDepartment.instance();
		
		for (DepartmentAssociation a : associations) {
			if (a.isRepresentative()) return a.getDepartment();
		}
		
		return getAssociations().get(0).getDepartment();
	}
	
	@JsonIgnore
	public Department getSelectedDepartment() {
		// NOTE Defensive coding. This should be removed and the system has to make sure every single user should have at least one department.
		if (getAssociations() == null || getAssociations().isEmpty()) return NullDepartment.instance();
		
		for (DepartmentAssociation a : getAssociations()) {
			if (a.isSelected()) return a.getDepartment();
		}
		
		return getDepartment();
	}
	
	@JsonIgnore
	public List<Department> getSelectedDepartmentHierarchy() {
		List<Department> departments = new ArrayList<Department>();
		
		for (Department d = getSelectedDepartment(); !d.isNull(); d = d.parent())
			departments.add(d);
		
		return departments;
	}
	
	public void selectDepartment(String departmentId) {
		Validate.notNull(departmentId, "department id is null.");
		Assert.isTrue(belongsToDepartment(departmentId), "user does not belong to dept id: " + departmentId);
		
		for (DepartmentAssociation a : getAssociations()) {
			if (a.getDepartment().getId().equals(departmentId)) a.setSelected(true);
			else a.setSelected(false);
		}
	}
	
	public boolean belongsToDepartment(String departmentId) {
		for (DepartmentAssociation a : getAssociations()) {
			if (a.getDepartment().getId().equals(departmentId)) return true;
		}
		
		return false;
	}

	@Override
	public Iterator<DepartmentAssociation> iterator() {
		return associations != null ? associations.iterator() : new ArrayList<DepartmentAssociation>().iterator();
	}
	
}
