package com.lgu.abc.core.prototype.org.user.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.core.base.security.role.company.CompanyMember;
import com.lgu.abc.core.base.security.role.company.CompanyRole;
import com.lgu.abc.core.base.utils.ChatUtils;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.prototype.org.user.CompanyRoleTester;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.domain.DepartmentAssociation;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.common.base.matchers.Matcher;
import com.lgu.abc.test.common.base.matchers.types.IterableContainedMatcher;

public abstract class AbstractUserFinderTest<T extends Identifiable> extends BaseTest {

	private AbstractUserFinder<T> finder;
		
	@Override
	public void setup() throws Exception {
		super.setup();
		
		this.finder = initFinder();
	}

	@Test
	public void testFindById() {
		final User user = getSession();
		T found = finder.findById(user.getId());
		
		logger.debug("found: " + found);
		assertThat(found.identical(create(user)), is(true));
	}
	
	@Test
	public void testFindByLoginId() {
		User user = getSession();
		T found = finder.findByLoginId(user.getLoginId(), user.getCompany().getId());
		
		assertThat(found.identical(create(user)), is(true));
	}
	
	@Test
	public void testFindByDepartmentId() {
		User user = getSession();
		Iterable<T> users = finder.findByDepartmentId(user.getSelectedDepartment().getId());
		
		for (T u : users) {
			logger.debug("user: " + u);
			boolean matched = false;
			for (DepartmentAssociation a : get(u).getAssociations()) {
				if (a.getDepartment().identical(user.getSelectedDepartment())) matched = true;
			}
			
			assertThat(matched, is(true));
		}
	}
	
	@Test
	public void testFindByCompanyId() {
		final User user = getSession();
		Iterable<T> users = finder.findByCompanyId(user.getCompany().getId());
		Matcher<T> matcher = new IterableContainedMatcher<T>(users);
		assertThat(matcher.matches(create(user)), is(true));
	}
	
	@Test
	public void testFindByMessengerId() {
		final User user = getSession();
		T found = finder.findByJabberId(ChatUtils.jid(user));
		assertThat(get(found).identical(user), is(true));
	}
		
	@Test
	public void testFindByOrganization() {
		final User actor = getSession();
		final User other1 = getSessionFactory().getFirstOther();
		final User other2 = getSessionFactory().getLastOther();
		
		List<String> userIds = new ArrayList<String>();
		userIds.add(other1.getId());
		userIds.add(other2.getId());
		
		List<String> departmentIds = new ArrayList<String>();
		departmentIds.add(actor.getDepartment().getId());
		departmentIds.add(other1.getDepartment().getId());
		
		Iterable<T> found = finder.findByOrganization(userIds, departmentIds);
		Matcher<T> matcher = new IterableContainedMatcher<T>(found);
		
		assertThat(matcher.matches(create(actor)), is(true));
		assertThat(matcher.matches(create(other1)), is(true));
		assertThat(matcher.matches(create(other2)), is(true));
	}
	
	@Test
	public void testFindByDepartmentIds() {
		final User actor = getSession();
		final User other1 = getSessionFactory().getFirstOther();
		
		List<String> departmentIds = new ArrayList<String>();
		departmentIds.add(actor.getDepartment().getId());
		departmentIds.add(other1.getDepartment().getId());
		
		Iterable<T> found = finder.findByOrganization(null, departmentIds);
		Matcher<T> matcher = new IterableContainedMatcher<T>(found);
		
		assertThat(matcher.matches(create(actor)), is(true));
		assertThat(matcher.matches(create(other1)), is(true));
	}
	
	@Test
	public void testFindByUserIds() {
		final User actor = getSession();
		final User other1 = getSessionFactory().getFirstOther();
		final User other2 = getSessionFactory().getLastOther();
		
		List<String> userIds = new ArrayList<String>();
		userIds.add(other1.getId());
		userIds.add(other2.getId());
		
		Iterable<T> found = finder.findByOrganization(userIds, null);
		Matcher<T> matcher = new IterableContainedMatcher<T>(found);
		
		assertThat(matcher.matches(create(actor)), is(false));
		assertThat(matcher.matches(create(other1)), is(true));
		assertThat(matcher.matches(create(other2)), is(true));
	}
	
	@Test
	public void testFindByCompanyAdministrator() {
		// Given
		final String companyId = getSession().getCompany().getId();
		final CompanyRole role = new CompanyAdministrator(companyId);
		
		// When
		Iterable<T> found = finder.findByCompanyRole(role);
				
		// Then
		new CompanyRoleTester(role.companyId(), role, true).test(transform(found));
	}
	
	@Test
	public void testFindByCompanyMember() {
		// Given
		final String companyId = getSession().getCompany().getId();
		final CompanyRole role = new CompanyMember(companyId);
		
		// When
		Iterable<T> found = finder.findByCompanyRole(role);
		
		// Then
		new CompanyRoleTester(role.companyId(), role, true).test(transform(found));
	}
	
	@Test
	public void testSearchWithText() {
		// Given
		final String companyId = getSession().getCompany().getId();
		
		// When
		Iterable<T> found = finder.search(getSession().getName().toString().substring(0, 2), companyId);
		
		// Then
		assertThat(found.iterator().hasNext(), is(true));
		Matcher<T> matcher = new IterableContainedMatcher<T>(found);
		assertThat(matcher.matches(create(getSession())), is(true));
	}
	
	@Test
	public void testSearchWithSearcher() {
		// Given
		final UserSearcher searcher = new UserSearcher(getSession().getName().toString().substring(0, 2), getSession().getCompany().getId());
		searcher.setDepartmentId(getSession().getDepartment().getId());
		
		// When
		Iterable<T> found = finder.search(searcher);
		
		// Then
		assertThat(found.iterator().hasNext(), is(true));
		Matcher<T> matcher = new IterableContainedMatcher<T>(found);
		assertThat(matcher.matches(create(getSession())), is(true));
	}
	
	private Iterable<User> transform(Iterable<T> found) {
		List<User> users = new ArrayList<User>();
		for (T entity : found) users.add(get(entity));
		
		return users;
	}
	
	protected abstract T create(User user);
	
	protected abstract User get(T entity);
	
	protected abstract AbstractUserFinder<T> initFinder();
	
}
