package com.lgu.abc.core.base.security.role;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.Permission;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.user.User;

public class DefaultUserRole extends AbstractRole {

	public DefaultUserRole(String userId) {
		ResourceID root = new ResourceID(User.class, userId);
				
		initializeCalendarRole(root);
	}
	
	private void initializeCalendarRole(ResourceID root) {
//		Permissible permission = new Permission(root.concatenate(new ResourceID(Calendar.class)), new Privilege(AccessLevel.MANAGE));
		Permissible permission = new Permission(root, new Privilege(AccessLevel.MANAGE));
		add(permission);
	}

}
