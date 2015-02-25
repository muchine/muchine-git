package com.lgu.abc.core.base.security.visitor;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permission;

public interface PermissibleVisitor {

	void visit(AbstractRole role);
	
	void visit(Permission permission);
	
}
