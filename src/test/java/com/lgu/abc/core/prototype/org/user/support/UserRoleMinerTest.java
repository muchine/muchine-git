package com.lgu.abc.core.prototype.org.user.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.test.common.base.BaseTest;

public class UserRoleMinerTest extends BaseTest {

	@Test
	public void testIsCompanyAdministrator() {
		assertThat(UserRoleMiner.isCompanyAdministrator(getSession()), is(true));
	}
		
}
