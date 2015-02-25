package com.lgu.abc.core.base.security.visitor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.test.common.base.BaseTest;

public class RoleMatcherTest extends BaseTest {

	@Test
	public void testCompanyAdministrator() {
		CompanyAdministrator adminRole = new CompanyAdministrator(getSession().getCompany().getId());
		
		RoleMatcher matcher = new RoleMatcher(getSession().getRole(), adminRole);
		assertThat(matcher.matches(), is(true));
		
		matcher = new RoleMatcher(getSessionFactory().getFirstOther().getRole(), adminRole);
		assertThat(matcher.matches(), is(false));
	}
	
	@Test
	public void testOtherCompanyAdministrator() {
		CompanyAdministrator adminRole = new CompanyAdministrator("0");
		
		RoleMatcher matcher = new RoleMatcher(getSession().getRole(), adminRole);
		assertThat(matcher.matches(), is(false));
	}
	
}
