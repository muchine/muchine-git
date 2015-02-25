package com.lgu.abc.core.prototype.org.user.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.vo.contact.domain.Email;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;

public class UserFinderTest extends AbstractUserFinderTest<User> {

	@Autowired
	private UserFinder finder;
			
	@Test
	public void testFindByEmail() {
		User user = finder.findByEmail(new Email(getSession().getEmail()));
		assertThat(user.identical(getSession()), is(true));
	}
	
	@Test
	public void testFindByNames() {
		// Given
		final String name = getSession().getName().toString();
		final List<String> names = new ArrayList<String>();
		names.add(name);
		
		// When
		Iterable<User> found = finder.findByNames(getSession().getCompany().getId(), names);
		
		// Then
		assertThat(found.iterator().hasNext(), is(true));
		for (User user : found) assertThat(user.getName().toString(), is(name));
	}

	@Test
	public void testFindCompanyAdministrator() {
		// Given
		Company company = getSession().getCompany();
		
		// When
		User found = finder.findCompanyAdministrator(company);
		
		// Then
		assertCompanyAdministrator(found, company);
	}
	
	@Test
	public void testFindCompanyAdministrators() {
		// Given
		Company company = getSession().getCompany();
		
		// When
		Iterable<User> found = finder.findCompanyAdministrators(company.getId());
		
		// Then
		assertThat(IterableUtils.isEmpty(found), is(false));
		for (User user : found) {
			assertCompanyAdministrator(user, company);
		}
	}
	
	private void assertCompanyAdministrator(User user, Company company) {
		logger.debug("found: " + user);
		assertThat(user.getCompany().identical(company), is(true));
		assertThat(UserRoleMiner.isCompanyAdministrator(user), is(true));	
	}
	
	@Test
	public void testFindDepartmentManager() {
		// Given
		Department department = getSession().getDepartment();
		
		// When
		User found = finder.findDepartmentManager(department);
		
		// Then
		logger.debug("found: " + found);
		assertThat(found.belongsToDepartment(department.getId()), is(true));
		assertThat(UserRoleMiner.isDepemartmentManager(found, department), is(true));
	}
	
	@Override
	protected User create(User user) {
		return user;
	}

	@Override
	protected User get(User entity) {
		return entity;
	}
	
	@Override
	protected AbstractUserFinder<User> initFinder() {
		return finder;
	}
	
}
