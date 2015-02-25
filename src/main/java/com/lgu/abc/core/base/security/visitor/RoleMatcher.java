package com.lgu.abc.core.base.security.visitor;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;

public class RoleMatcher implements PermissibleVisitor {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final AbstractRole navigated;
	
	private final AbstractRole role;
	
	private boolean matched = false;
	
	public RoleMatcher(AbstractRole navigated, AbstractRole role) {
		Validate.notNull(navigated, "navigated role is null.");
		Validate.notNull(role, "role is null.");
		
		logger.trace("role: " + role + ", navigated: " + navigated);
		
		this.navigated = navigated;
		this.role = role;
	}
	
	@Override
	public void visit(AbstractRole role) {
		logger.trace("exploring role: " + role);
		
		if (matched || this.role.identical(role)) {
			matched = true;
			return;
		}
		
		for (Permissible permissible : role) permissible.accept(this);
	}

	@Override
	public void visit(Permission permission) {

	}
	
	public boolean matches() {
		navigated.accept(this);
		return matched;
	}
	
}
