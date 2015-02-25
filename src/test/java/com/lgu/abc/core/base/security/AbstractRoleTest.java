package com.lgu.abc.core.base.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.core.base.security.role.company.CompanyAdministrator;
import com.lgu.abc.core.base.security.role.department.DepartmentManager;

public class AbstractRoleTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	@Test
	public void testIdentical() {
		CompanyAdministrator a = new CompanyAdministrator("1");
		CompanyAdministrator b = new CompanyAdministrator("1");
			
		logger.debug("a: " + a + ", b: " + b);
		assertThat(a.identical(b), is(true));
	}
	
	@Test
	public void testAddEquivalentRole() {
		DepartmentManager leader = new DepartmentManager("10");
				
		TestRole role = new TestRole();
		role.add(leader);
				
		logger.debug(role);
		assertThat(role.permissibles.size(), is(1));
	}
	
	private static final class TestRole extends AbstractRole {}
	
}
