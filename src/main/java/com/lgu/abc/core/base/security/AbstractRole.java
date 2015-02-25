package com.lgu.abc.core.base.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.security.visitor.PermissibleVisitor;
import com.lgu.abc.core.base.security.visitor.PermissionCollector;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;

@ToString(includeFieldNames=true, exclude="logger") @EqualsAndHashCode(exclude="logger")
public abstract class AbstractRole implements Permissible, Iterable<Permissible> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected final Set<Permissible> permissibles = new HashSet<Permissible>();
	
	private String resourceId;
	
	/*
	 * Constructor
	 */
	public AbstractRole() {}
	
	protected AbstractRole(String resourceId) {
		Validate.notNull(resourceId, "resource id is null.");
		this.resourceId = resourceId;
		
		Collection<Permissible> permissibles = initialize(resourceId);
		if (permissibles != null) add(permissibles);
	}
	
	public boolean identical(AbstractRole role) {
		if (role == null || !role.getClass().equals(getClass())) return false;
		return role.permissibles.containsAll(this.permissibles);
	}
	
	public String resourceId() {
		return resourceId;
	}
	
	/*
	 * Element Manipulation
	 */
	public void add(Permissible permissible) {
		this.permissibles.add(permissible);
	}
	
	public void add(Collection<Permissible> permissibles) {
		this.permissibles.addAll(permissibles);
	}
	
	public void remove(Permissible permissible) {
		this.permissibles.remove(permissible);
	}
	
	@Override
	public Privilege privilege(ResourceID resourceId) {
		PermissionCollector visitor = new PermissionCollector();
		visitor.visit(this);
		
		List<Permission> permissions = visitor.getPermissions();
		Collections.sort(permissions);
		
		for (Permissible p : permissions) {
			if (p.privilege(resourceId) != null) {
				logger.trace("found matched permission: " + p + " for resource id: " + resourceId);
				return p.privilege(resourceId);
			}
		}
		
		logger.trace("No matched permission for resource id: " + resourceId);
		return null;
	}
		
	@Override
	public void accept(PermissibleVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Iterator<Permissible> iterator() {
		return permissibles.iterator();
	}
		
	/*
	 * Hook method.
	 * 
	 * CAUTION Be very careful the order of permissions because permission sorting functionality has not yet been implemented.
	 * Permission found first will be checked first when resource patterns of permissions conflicts.
	 */
	protected Collection<Permissible> initialize(String rootId) {
		return null;
	}
	
}
