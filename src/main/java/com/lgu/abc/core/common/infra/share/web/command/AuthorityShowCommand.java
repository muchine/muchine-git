package com.lgu.abc.core.common.infra.share.web.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.web.view.command.AbstractCommand;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class AuthorityShowCommand extends AbstractCommand {

	private String id;
	
	private boolean shown;
	
	private final Authority authority = new Authority();
		
	@Override
	public RootEntity update(RootEntity retrieved) {
		Authority casted = (Authority) retrieved;
		authority.setTableName(casted.getTableName());
		authority.setEntityId(casted.getEntityId());
		
		Accessor accessor = casted.getAccessor();
		authority.setAccessor(new Accessor(accessor.getParty(), accessor.getPrivilege(), shown));
		
		return authority;
	}
	
	@Override
	public RootEntity read() {
		authority.setId(id);
		return authority;
	}
	
}
