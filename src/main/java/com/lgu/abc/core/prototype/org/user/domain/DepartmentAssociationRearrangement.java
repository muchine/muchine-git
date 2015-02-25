package com.lgu.abc.core.prototype.org.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.repository.vo.Rearrangement;
import com.lgu.abc.core.common.domain.AbstractRearrangement;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class DepartmentAssociationRearrangement extends AbstractRearrangement {
	
	private final String departmentId;
	
	public DepartmentAssociationRearrangement(String departmentId, int from, int to) {
		super(new Rearrangement(from, to));
		
		Validate.notNull(departmentId, "department id is null.");
		this.departmentId = departmentId;
	}
	
}
