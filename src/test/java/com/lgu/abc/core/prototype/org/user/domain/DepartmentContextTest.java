package com.lgu.abc.core.prototype.org.user.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.lgu.abc.core.prototype.org.OrganizationGenerator;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.domain.DepartmentContext;

public class DepartmentContextTest {

	public OrganizationGenerator generator = new OrganizationGenerator();
	
	@Test
	public void testGetSelectedDepartmentHierarchy() {
		DepartmentContext context = generator.getU3().getDepartmentContext();
		List<Department> hierarchy = context.getSelectedDepartmentHierarchy();
		
		Department parent = generator.getD3();
		
		assertThat(hierarchy.get(0).identical(parent), is(true));
		assertThat(hierarchy.get(1).identical(parent.parent()), is(true));
	}
	
	@Test
	public void testBelongToDepartment() {
		DepartmentContext context = generator.getU3().getDepartmentContext();
		assertThat(context.belongsToDepartment(generator.getD3().getId()), is(true));
		assertThat(context.belongsToDepartment(generator.getD1().getId()), is(false));
	}
	
}
