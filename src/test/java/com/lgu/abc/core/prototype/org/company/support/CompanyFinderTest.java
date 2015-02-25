package com.lgu.abc.core.prototype.org.company.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.domain.CompanyDomain;
import com.lgu.abc.test.common.base.BaseTest;

public class CompanyFinderTest extends BaseTest {

	@Autowired
	private CompanyFinder finder;
	
	@Test
	public void testFindAll() {
		// When
		Iterable<Company> found = finder.findAll();
		
		// Then
		assertThat(IterableUtils.isEmpty(found), is(false));
		logger.debug("found size: " + IterableUtils.toList(found).size());
	}
	
	@Test
	public void testFindByPrimaryDomain() {
		// Given
		Company company = getSession().getCompany();
		
		// When
		Company found = finder.findByPrimaryDomain(company.getDomain());
		
		// Then
		logger.debug("found: " + found);
		assertThat(company.identical(found), is(true));
		assertThat(found.getDomain(), is(company.getDomain()));
	}
	
	@Test
	public void testFindByAccessDomain() {
		// Given
		Company company = getSession().getCompany();
		String domain = getAccessDomain(company);
		logger.debug("domain: " + domain);
		
		// When
		Company found = finder.findByAccessDomain(domain);
		
		// Then
		logger.debug("found: " + found);
		assertThat(company.identical(found), is(true));
		assertThat(hasAccessDomain(found, domain), is(true));
	}
	
	@Test
	public void testFindByPrimaryOrAccessDomain() {
		// Given
		Company company = getSession().getCompany();
		
		// When
		Company foundByPrimaryDomain = finder.findByPrimaryOrAccessDomain(company.getDomain());
		Company foundByAccessDomain = finder.findByAccessDomain(company.getDomains().get(0).getName());
		
		// Then
		assertThat(foundByPrimaryDomain.identical(company), is(true));
		assertThat(foundByAccessDomain.identical(company), is(true));
	}
	
	private String getAccessDomain(Company company) {
		List<CompanyDomain> domains = company.getDomains();
		for (CompanyDomain domain : domains) {
			if (!domain.isPrimary()) return domain.getName();
		}
		
		throw new IllegalArgumentException();
	}
	
	private boolean hasAccessDomain(Company company, String accessDomain) {
		for (CompanyDomain domain : company.getDomains()) {
			if (accessDomain.equals(domain.getName())) return true;
		}
		
		return false;
	}
	
}
