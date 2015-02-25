package com.lgu.abc.core.base.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.security.visitor.PermissibleVisitor;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;

@ToString(exclude="logger") @EqualsAndHashCode(exclude="logger")
public class Permission implements Permissible, Comparable<Permission> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ResourceID resourceId;
	
	private Privilege privilege;
	
	public Permission(ResourceID resourceId, Privilege privilege) {
		Validate.notNull(resourceId, "resource id is null.");
		Validate.notNull(privilege, "privilege is null.");
		
		this.resourceId = resourceId;
		this.privilege = privilege;
	}
	
	@Override
	public Privilege privilege(ResourceID resourceId) {
		return this.resourceId.matches(resourceId) ? privilege : null;
	}

	@Override
	public int compareTo(Permission o) {
		return resourceId.compareTo(o.resourceId);
	}

	@Override
	public void accept(PermissibleVisitor visitor) {
		visitor.visit(this);
	}
	
}
