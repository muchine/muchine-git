package com.lgu.abc.core.common.infra.share;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class AuthorityQuery extends BaseQuery {

	@NotNull
	private String tableName;

	private List<String> entityIds;
			
	private List<String> departmentIds;
	
	private String userId;
	
	public void findByCompanyId(String tableName, String companyId) {
		Validate.notNull(tableName, "table name is null.");
		Validate.notNull(companyId, "company id is null.");
		
		clear();
		
		this.tableName = tableName;
		setCompanyId(companyId);
	}
	
	public void findByDepartmentId(String tableName, String departmentId) {
		List<String> departmentIds = new ArrayList<String>();
		departmentIds.add(departmentId);
		
		findByDepartmentIds(tableName, departmentIds);
	}
	
	public void findByDepartmentIds(String tableName, List<String> departmentIds) {
		Validate.notNull(tableName, "table name is null.");
		Validate.noNullElements(departmentIds);
		
		clear();
		
		this.tableName = tableName;
		this.departmentIds = departmentIds;
	}
	
	public void findByUserId(String tableName, String userId) {
		Validate.notNull(tableName, "table name is null.");
		Validate.notNull(userId, "user id is null.");
		
		clear();
		
		this.tableName = tableName;
		this.userId = userId;
	}
	
	public void findAllAccessibleAuthorities(String tableName, User user) {
		Validate.notNull(tableName, "table name is null.");
		Validate.notNull(user, "user is null.");
		
		clear();
		
		this.tableName = tableName;
		this.userId = user.getId();
		
		this.departmentIds = new ArrayList<String>();
		for (Department d : user.getSelectedDepartmentHierarchy()) departmentIds.add(d.getId());

		setCompanyId(user.getCompany().getId());
	}
	
	public void findByEntityId(String tableName, String entityId) {
		Validate.notNull(tableName, "table name is null.");
		Validate.notNull(entityId, "entity id is null.");
		
		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entityId);
		
		findByEntityIds(tableName, entityIds);
	}
	
	public void findByEntityIds(String tableName, List<String> entityIds) {
		Validate.notNull(tableName, "table name is null.");
		Validate.noNullElements(entityIds, "entity id list is null.");
		
		clear();
		
		this.tableName = tableName;
		this.entityIds = entityIds;
	}
	
	@Override
	public void clear() {
		super.clear();
		
		tableName = null;
		entityIds = null;
		userId = null;
		departmentIds = null;
	}
	
}
