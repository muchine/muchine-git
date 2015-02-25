package com.lgu.abc.core.plugin.proto.user.role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.plugin.proto.user.role.selector.OrganizationalRoleSelectable;
import com.lgu.abc.core.plugin.proto.user.role.sort.OrganizationalRoleSorter;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class OrganizationalRoleRegistry {

	private static final Set<OrganizationalRole> roles = new HashSet<OrganizationalRole>();
	
	public synchronized void add(OrganizationalRole role) {
		PluggableUtils.add(role, roles);
	}
	
	public static OrganizationalRole find(String roleId, Company company) {
		return PluggableUtils.find(roleId, company, roles);
	}
	
	public static List<OrganizationalRole> find(OrganizationalRoleSelectable selectable, Company company) {
		List<OrganizationalRole> found = new ArrayList<OrganizationalRole>();
		for (OrganizationalRole role : enabled(company)) {
			if (selectable.canSelect(role)) found.add(role); 
		}
		
		Collections.sort(found, new OrganizationalRoleSorter(company));
		return found;
	}
	
	public static List<OrganizationalRole> enabled(Company company) {
		return PluggableUtils.enabled(company, roles);
	}
	
	public static Set<OrganizationalRole> all() {
		return PluggableUtils.all(roles);
	}
	
}
