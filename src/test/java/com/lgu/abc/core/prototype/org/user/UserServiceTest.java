package com.lgu.abc.core.prototype.org.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.core.base.security.role.company.CompanyMember;
import com.lgu.abc.core.base.security.role.company.CompanyRole;
import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.service.UserService;
import com.lgu.abc.core.prototype.org.user.support.DepartmentMatcher;
import com.lgu.abc.core.prototype.org.user.support.UserQueryBuilder;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.common.base.matchers.Matcher;
import com.lgu.abc.test.common.base.matchers.types.IterableContainedMatcher;

public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService service;
	
	@Test
	public void testReadUser() {
		User found = getUser();
		
		logger.debug("workspace role in role: " + found.getRole().getTransfered());
		for (Permissible p : found.getRole()) logger.debug("permission: " + p);
		
		// Then
		logger.debug("found: " + found);
		logger.debug("position: " + found.getPosition());
		logger.debug("title: " + found.getTitle());
		logger.debug("locale: " + found.getLocale());
		logger.debug("department associations: " + found.getAssociations());
		assertThat(found, is(notNullValue()));
		assertThat(found.getCompany().getDomain(), is(notNullValue()));
		assertThat(found.getSelectedDepartment().getName(), is(notNullValue()));
		assertThat(found.isNull(), is(false));
		assertThat(found.getAssociations().isEmpty(), is(false));
		assertThat(found.getRole().privilege(new ResourceID(Company.class, found.getCompany().getId())), is(notNullValue()));
	}

	@Test
	public void testReadCompanyOwnerEntity() {
		User user = getUser();
		
		TestActionEntity entity = new TestActionEntity();
		entity.setOwnable(user.getCompany());
		
		assertThat(user.canRead(entity), is(true));
	}
	
	@Test
	public void testHasRole() {
		CompanyAdministrator adminRole = new CompanyAdministrator(getSession().getCompany().getId());
		
		assertThat(getSession().hasRole(adminRole), is(true));
		assertThat(getSessionFactory().getFirstOther().hasRole(adminRole), is(false));
		
		CompanyAdministrator otherRole = new CompanyAdministrator("0");
		
		assertThat(getSession().hasRole(otherRole), is(false));
		assertThat(getSessionFactory().getFirstOther().hasRole(otherRole), is(false));
	}
		
	@Test
	public void testFindCompanyAdministrators() {
		findByCompanyRole(new CompanyAdministrator(getSession().getCompany().getId()));
	}
	
	@Test
	public void testFindCompanyMembers() {
		findByCompanyRole(new CompanyMember(getSession().getCompany().getId()));
	}
	
	private void findByCompanyRole(CompanyRole role) {
		// Given
		UserQueryBuilder builder = new UserQueryBuilder()
			.companyRole(role);
		
		// When
		Iterable<User> found = service.find(builder.build(), null);
		
		// Then
		new CompanyRoleTester(role.companyId(), role, true).test(found);
	}
	
	@Test
	public void testSearch() {
		// Given
		UserQueryBuilder builder = new UserQueryBuilder()
			.search(getSession().getName().toString().substring(0, 2));
		
		// When
		Iterable<User> found = service.find(builder.build(), null);
		
		// Then
		Matcher<User> matcher = new IterableContainedMatcher<User>(found);
		assertThat(matcher.matches(getSession()), is(true));
	}
	
	@Test
	public void testFindByLoginId() {
		// Given
		UserQueryBuilder builder = new UserQueryBuilder()
			.company(getSession().getCompany().getId())
			.loginId(getSession().getLoginId());
		
		// When
		Iterable<User> found = service.find(builder.build(), null);
		
		// Then
		Matcher<User> matcher = new IterableContainedMatcher<User>(found);
		assertThat(matcher.matches(getSession()), is(true));
		
		List<User> casted = IterableUtils.toList(found);
		assertThat(casted.size(), is(1));
	}
	
	@Test
	public void testFindByStopped() {
		// Given
		UserQueryBuilder builder = new UserQueryBuilder()
			.company(getSession().getCompany().getId())
			.stopped(true);
		
		// When
		Iterable<User> found = service.find(builder.build(), null);
		
		// Then
		Matcher<User> matcher = new IterableContainedMatcher<User>(found);
		assertThat(matcher.matches(getSession()), is(false));
		
		for (User user : found) {
			assertThat(user.isStopped(), is(true));
		}
	}
	
	@Test
	public void testFindByOrganizationWithUser() {
		// Given
		List<String> userIds = new ArrayList<String>();
		userIds.add(getSession().getId());
		userIds.add(getSessionFactory().getFirstOther().getId());
		
		// When
		Iterable<User> found = testFindByOrganization(userIds, null);
		
		// Then
		List<User> users = IterableUtils.toList(found);
		assertThat(users.size(), is(2));
		
		Matcher<User> matcher = new IterableContainedMatcher<User>(found);
		assertThat(matcher.matches(getSession()), is(true));
		assertThat(matcher.matches(getSessionFactory().getFirstOther()), is(true));
	}
	
	@Test
	public void testFindByOrganizationWithDepartment() {
		// Given
		Department department1 = getSessionFactory().getFirstOther().getDepartment();
		Department department2 = getSession().getDepartment();
		
		List<String> departmentIds = new ArrayList<String>();
		departmentIds.add(department1.getId());
		departmentIds.add(department2.getId());
		
		// When
		Iterable<User> found = testFindByOrganization(null, departmentIds);
		
		// Then
		assertThat(IterableUtils.isEmpty(found), is(false));
		for (User user : found) {
			Matcher<Department> matcher = new DepartmentMatcher(user);
			assertThat(matcher.matches(department1) || matcher.matches(department2), is(true));	
		}		
	}
		
	@Test
	public void testFindByOrganizationWithBoth() {
		// Given
		User user1 = getSession();
		User user2 = getSessionFactory().getFirstOther();
		
		List<String> userIds = new ArrayList<String>();
		userIds.add(user1.getId());
		userIds.add(user2.getId());
		
		Department department1 = getSessionFactory().getFirstOther().getDepartment();
		Department department2 = getSession().getDepartment();
		
		List<String> departmentIds = new ArrayList<String>();
		departmentIds.add(department1.getId());
		departmentIds.add(department2.getId());

		// When
		Iterable<User> found = testFindByOrganization(userIds, departmentIds);
		
		// Then
		assertThat(IterableUtils.isEmpty(found), is(false));
		for (User user : found) {
			Matcher<Department> matcher = new DepartmentMatcher(user);
			if (matcher.matches(department1) || matcher.matches(department2) ||
					user1.identical(user) || user2.identical(user)) continue;
			
			fail("A user does not match with department. user: " + user);
		}		
	}
		
	private Iterable<User> testFindByOrganization(List<String> userIds, List<String> departmentIds) {
		// Given
		UserQueryBuilder builder = new UserQueryBuilder()
			.organization(userIds, departmentIds);
		
		// When
		Iterable<User> found = service.find(builder.build(), null);
		
		// Then
		for (User user : found) {
			logger.debug("found: " + user);
		}
		
		return found;
	}
	
	private User getUser() {
		return service.read(getSession());
	}
	
	private static class TestActionEntity extends RootEntity {}
	
}
