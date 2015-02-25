package com.lgu.abc.core.prototype.org.department.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.common.base.matchers.Matcher;
import com.lgu.abc.test.common.base.matchers.types.IterableContainedMatcher;

public class DepartmentFinderTest extends BaseTest {

	@Autowired
	private DepartmentFinder finder;
	
	@Test
	public void testFindByCompanyId() {
		Company company = getSession().getCompany();
		
		Iterable<Department> found = finder.findByCompanyId(company.getId());
		
		for (Department department : found) {
			logger.debug("department: " + department);
			assertThat(department.getCompany().identical(company), is(true));
		}
	}
	
	@Test
	public void testFindByParentId() {
		Department deepest = deepest();
		Iterable<Department> found = finder.findByParentId(deepest.getCompany().getId(), deepest.parent().getId());
		
		assertThat(found.iterator().hasNext(), is(true));
		for (Department department : found) {
			logger.debug("found: " + department);
			assertThat(department.parent().identical(deepest.parent()), is(true));
		}
	}
	
	@Test
	public void testFindByName() {
		// Given
		Department department = getSession().getDepartment();
		
		// When
		Iterable<Department> found = finder.findByName(getSession().getCompany().getId(), department.getName().toString());
		
		// Then
		Matcher<Department> matcher = new IterableContainedMatcher<Department>(found);
		assertThat(matcher.matches(department), is(true));
	}
	
	@Test
	public void testFindVirtualDepartment() {
		Department department = finder.findVirtualDepartment(getSession().getCompany().getId());
		logger.debug("found: " + department);
		assertThat(department.isVirtual(), is(true));
	}
	
	private Department deepest() {
		Iterable<Department> departments = finder.findByCompanyId(getSession().getCompany().getId());
		
		Department deepest = null;
		for (Department department : departments) {
			if (deepest == null || deepest.depth() < department.depth()) deepest = department;
		}
		
		return deepest;
	}
	
}
