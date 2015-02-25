package com.lgu.abc.core.prototype.org.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.security.AbstractRole;
import com.lgu.abc.core.prototype.org.user.User;

public class CompanyRoleTester {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String companyId;
	
	private final AbstractRole role;
	
	private final boolean hasRole;	
	
	public CompanyRoleTester(String companyId, AbstractRole role, boolean hasRole) {
		this.companyId = companyId;
		this.role = role;
		this.hasRole = hasRole;
	}
	
	public void test(Iterable<User> found) {
		// Then
		assertThat(found.iterator().hasNext(), is(true));
		for (User user : found) {
			logger.debug("found user: " + user);
			assertThat(user.getCompany().getId(), is(companyId));
			assertThat(user.hasRole(role), is(hasRole));
		}
		
	}
	
	
}
