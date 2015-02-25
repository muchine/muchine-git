package com.lgu.abc.core.base.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.mybatis.handlers.query.interfaces.ManageableQuery;
import com.lgu.abc.core.mybatis.handlers.query.interfaces.Queryable;
import com.lgu.abc.core.mybatis.handlers.query.interfaces.ReadableQuery;
import com.lgu.abc.core.mybatis.handlers.query.interfaces.WritableQuery;

@ToString(callSuper = true, includeFieldNames = true) @EqualsAndHashCode(callSuper = true)
public @Data class AuthorizableQuery extends BaseQuery {

	@JsonIgnore
	private Queryable queryable = new ReadableQuery();
	
	public boolean canQuery(Containable containable) {
		queryable.setParty(getActor());
		return queryable.canQuery(containable);
	}

	public void findAllReadable() {
		findWithQueryable(new ReadableQuery());
	}
	
	public void findAllWritable() {
		findWithQueryable(new WritableQuery());
	}
	
	public void findAllManageable() {
		findWithQueryable(new ManageableQuery());
	}
	
	protected void findWithQueryable(Queryable queryable) {
		setOwnable(getActor());
		setQueryable(queryable);
	}
	
}
