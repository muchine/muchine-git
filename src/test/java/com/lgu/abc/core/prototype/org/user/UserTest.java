package com.lgu.abc.core.prototype.org.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lgu.abc.core.base.security.role.department.DepartmentManager;
import com.lgu.abc.core.prototype.org.user.domain.roles.transfer.DepartmentRoleTO;
import com.lgu.abc.core.prototype.org.user.domain.roles.transfer.RoleTO;

public class UserTest {

	@Test
	public void testHasDepartmentRoles() {
		User user = new User("100");
		RoleTO to = user.getRole().getTransfered();
		
		List<DepartmentRoleTO> departments = new ArrayList<DepartmentRoleTO>();
		departments.add(new DepartmentRoleTO("10", false));
		departments.add(new DepartmentRoleTO("11", true));
		to.setDepartments(departments);
		
		user.initialize();
		
		assertThat(user.hasRole(new DepartmentManager("10")), is(false));
		assertThat(user.hasRole(new DepartmentManager("11")), is(true));
	}
	
}
