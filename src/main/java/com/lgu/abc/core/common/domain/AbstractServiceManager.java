package com.lgu.abc.core.common.domain;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.Length;

import com.lgu.abc.core.base.domain.action.CompanyManagementEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.vo.time.day.Day;
import com.lgu.abc.core.prototype.org.user.User;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data abstract class AbstractServiceManager extends CompanyManagementEntity {
	
	private boolean used;
	
	@Length(min = 8, max = 8)
	private String start;
	
	@Length(min = 8, max = 8)
	private String end;
	
	@Override
	public void setActor(User actor) {
		super.setActor(actor);
		setId(actor.getCompany().getId());
	}
	
	public void subscribe() {
		if (!used) start = new Day(new Date()).date();
		end = null;
		used = true;
	}
	
	public void unsubscribe() {
		end = new Day(new Date()).date();
		used = false;
	}

	@Override
	public RootEntity update(RootEntity entity) {
		super.update(entity);
		
		AbstractServiceManager casted = (AbstractServiceManager) entity;
		setUsed(casted.isUsed());
		setStart(casted.getStart());
		setEnd(casted.getEnd());
		
		return this;
	}
	
}
