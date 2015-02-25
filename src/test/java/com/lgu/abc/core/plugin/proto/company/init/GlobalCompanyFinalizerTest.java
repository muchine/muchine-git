package com.lgu.abc.core.plugin.proto.company.init;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.test.common.base.BaseTest;

public class GlobalCompanyFinalizerTest extends BaseTest {

	@Autowired
	private GlobalCompanyFinalizer finalizer;
	
	@Autowired
	private GlobalExternalCompanyFinalizer externalFinalizer;
	
	@Before
	public void setup() {
		externalFinalizer.setEnabled(false);
	}
	
	@Test
	public void testAll() {
		for (CompanyFinalizable finalizable : finalizer.all()) {
			logger.debug("finalizer: " + finalizable.getClass());
		}
	}
	
	@Test
	public void testFinalize() {
		Company company = getSession().getCompany();
		finalizer.finalize(company);
	}
	
}
