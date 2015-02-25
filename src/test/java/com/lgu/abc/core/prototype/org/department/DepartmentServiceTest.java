package com.lgu.abc.core.prototype.org.department;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.DepartmentQuery;
import com.lgu.abc.core.prototype.org.department.service.DepartmentService;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.test.common.base.BaseTest;

public class DepartmentServiceTest extends BaseTest {

	private static final String childId = "34";
	
	@Autowired 
	private DepartmentService service;
	
	@Test
	public void testReadDepartment() {
		Department found = deepest();
		
		// Then
		logger.debug("found: " + found);
		logger.debug("parent: " + found.parent());
		logger.debug("parent of parent: " + found.parent().parent());
		logger.debug("parent of parent of parent: " + found.parent().parent().parent());
		
		assertThat(found, is(notNullValue()));
		assertThat(found.isNull(), is(false));
		assertThat(found.getCompany(), is(notNullValue()));
	}
	
	@Test
	public void testFindByParentId() {
		Department department = deepest();
		
		DepartmentQuery query = new DepartmentQuery();
		query.setCompanyId(department.getCompany().getId());
		query.setParentId(department.parent().getId());
		
		Iterable<Department> found = service.find(query, null);
		assertThat(found.iterator().hasNext(), is(true));
		boolean matched = false;
		for (Department d : found) {
			logger.debug("found: " + d);
			assertThat(d.parent().identical(department.parent()), is(true));
			if (d.identical(department)) matched = true;
		}
		
		assertThat(matched, is(true));
	}
	
	private Department deepest() {
		// Given
		User user = getSession();
		Department department = new Department(user.departments().get(0));

		/*
		 * NOTE set sequence to test getting department hierarchy
		 */
		department.setId(childId);
		
		// When
		return service.read(department);
	}
	
}
