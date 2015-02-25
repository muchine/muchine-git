package com.lgu.abc.core.base.domain.query;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Null;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.common.interfaces.Ownable;
import com.lgu.abc.core.support.annotation.Query;
import com.lgu.abc.core.support.annotation.Query.Type;

@ToString(callSuper = true, includeFieldNames = true) @EqualsAndHashCode(callSuper = true)
public @Data abstract class BaseQuery extends BaseEntity {
	
	private Ownable ownable;
	
	private boolean deleted = false;
	
	private Pageable pageable;
	
	@Null
	private String companyId;
	
	private List<String> ids;
	
	public void addId(String id) {
		Validate.notNull(id, "entity id is null.");
		
		if (ids == null) ids = new ArrayList<String>();
		ids.add(id);
	}
	
	public void clear() {
		ids = null;
		ownable = null;
		companyId = null;
	}
	
	public final Type[] queryTypes() {
		Query annotation = getClass().getAnnotation(Query.class);
		return annotation == null ? null : annotation.value();
	}
	
	public final String queryTable() {
		Query annotation = getClass().getAnnotation(Query.class);
		return annotation == null ? null : annotation.table();
	}
	
}
