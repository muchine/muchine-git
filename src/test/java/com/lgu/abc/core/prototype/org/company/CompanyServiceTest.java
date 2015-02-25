package com.lgu.abc.core.prototype.org.company;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.CompanyQuery;
import com.lgu.abc.core.prototype.org.company.service.CompanyService;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.common.base.matchers.Matcher;
import com.lgu.abc.test.common.base.matchers.types.IterableContainedMatcher;

public class CompanyServiceTest extends BaseTest {
	
	@Autowired 
	private CompanyService service;
	
	@Test
	public void testReadCompany() {
		// Given
		User user = getSession();
		
		// When
		Company company = new Company(user.getCompany().getId());
		Company found = service.read(company);
		
		// Then
		logger.debug("found: " + found);
		assertThat(found.identical(user.getCompany()), is(true));
	}
	
	@Test
	public void testFindCompanyByDomain() {
		// Given
		Company company = getSession().getCompany();
		
		CompanyQuery query = new CompanyQuery();
		query.setDomain(company.getDomain());
		
		// When
		Iterable<Company> found = service.find(query, null);
		
		// Then
		Matcher<Company> matcher = new IterableContainedMatcher<Company>(found);
		assertThat(matcher.matches(company), is(true));
		
		for (Company c : found) assertThat(c.getDomain(), is(company.getDomain()));
	}
	
}
