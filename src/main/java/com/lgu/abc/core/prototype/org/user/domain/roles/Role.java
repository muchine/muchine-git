package com.lgu.abc.core.prototype.org.user.domain.roles;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.role.DefaultUserRole;
import com.lgu.abc.core.common.interfaces.LazyLoadable;
import com.lgu.abc.core.prototype.org.user.domain.roles.transfer.RoleTO;

@ToString(callSuper = true, includeFieldNames = true, exclude="transfered") @EqualsAndHashCode(callSuper = true)
public @Data class Role extends AbstractRole implements LazyLoadable {
	
	@JsonIgnore
	private RoleTO transfered = new RoleTO();
	
	public void initializeUserRole(String userId) {
		add(new DefaultUserRole(userId));
	}
	
	public boolean isInitialized() {
		return transfered.isInitialized();
	}

	@Override
	public void load() {
		logger.trace("initialize role...");
		transfered.initialize(this);
		
		logger.trace(permissibles);
	}

}
