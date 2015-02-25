package com.lgu.abc.core.prototype.org.department;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.query.BaseQuery;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class DepartmentQuery extends BaseQuery {

	private String parentId;
	
	private String name;
	
}
