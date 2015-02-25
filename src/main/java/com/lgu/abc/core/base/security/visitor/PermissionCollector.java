package com.lgu.abc.core.base.security.visitor;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;

public class PermissionCollector implements PermissibleVisitor {

	@Getter
	private final List<Permission> permissions = new ArrayList<Permission>();
	
	@Override
	public void visit(AbstractRole role) {
		for (Permissible permissible : role) permissible.accept(this);
	}

	@Override
	public void visit(Permission permission) {
		permissions.add(permission);
	}

}
