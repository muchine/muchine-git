package com.lgu.abc.core.common.file.validation.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import com.lgu.abc.core.common.file.validation.spec.CompanyFileSpecification;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.test.common.base.BaseTest;

public class CompanyFileSpecificationTest extends BaseTest {

	@Test
	public void test() {
		final Company company = getSession().getCompany();
		test(new CompanyFileSpecification(company), true);
	}
	
	@Test
	public void testOtherCompany() {
		final Company company = getSessionFactory().getOtherCompanyUser().getCompany();
		test(new CompanyFileSpecification(company), false);
	}
	
	private void test(Specification spec, boolean satisfied) {
		Collection<File> files = FileSpecificationFixtureFactory.files(1, new Size(10, Unit.MB));
		for (File file : files) file.setOwnable(getSession());
		
		assertThat(spec.isSatisfiedBy(files), is(satisfied));
	}
	
}
