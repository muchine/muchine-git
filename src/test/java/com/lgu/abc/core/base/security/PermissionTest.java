package com.lgu.abc.core.base.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;

public class PermissionTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	@Test
	public void testHashCode() {
		ResourceID resource = new ResourceID("company", "1");
		
		Permission a = new Permission(resource, new Privilege(AccessLevel.MANAGE));
		Permission b = new Permission(resource, new Privilege(AccessLevel.MANAGE));
		
		logger.debug("a: " + a.hashCode() + ", b: " + b.hashCode());
		assertThat(a.hashCode(), is(b.hashCode()));
	}
	
	@Test
	public void testEqual() {
		ResourceID resource = new ResourceID("company", "1");
		
		Permission a = new Permission(resource, new Privilege(AccessLevel.MANAGE));
		Permission b = new Permission(resource, new Privilege(AccessLevel.MANAGE));
		
		assertThat(a.equals(b), is(true));
	}
	
}
