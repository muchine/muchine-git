package com.lgu.abc.core.common.vo.id;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ResourceIDTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void testMatchIdentical() {
		ResourceID target = new ResourceID("company", "1");
		ResourceID compared = new ResourceID("company", "1");
		
		assertThat(target.matches(compared), is(true));
	}
	
	@Test
	public void testMatchWithWildcard() {
		ResourceID target = new ResourceID("company", "1");
		ResourceID compared = new ResourceID("company");
		
		assertThat(target.matches(compared), is(true));
	}
	
	@Test
	public void testMatchContained() {
		ResourceID target = new ResourceID("company", "1");
		ResourceID compared = new ResourceID("company", "1");
		compared.concatenate(new ResourceID("calendar", "10"));
		
		assertThat(target.matches(compared), is(true));
	}
	
	@Test
	public void testMatchReverseContained() {
		ResourceID target = new ResourceID("company", "1");
		ResourceID compared = new ResourceID("company", "1");
		target.concatenate(new ResourceID("calendar", "10"));
		
		assertThat(target.matches(compared), is(false));
	}
	
	@Test
	public void testCompareResourceID() {
		ResourceID a = new ResourceID("company", "1");
		
		ResourceID b = new ResourceID("company", "1").
				concatenate(new ResourceID("calendar"));
		
		ResourceID c = new ResourceID("company", "1").
				concatenate(new ResourceID("calendar", "10"));
		
		List<ResourceID> ids = new ArrayList<ResourceID>();
		ids.add(a);
		ids.add(b);
		ids.add(c);
		
		logger.debug("before sort: " + ids);
		
		Collections.sort(ids);
		
		logger.debug("after sort: " + ids);
		assertThat(ids.get(0), is(c));
		assertThat(ids.get(1), is(b));
		assertThat(ids.get(2), is(a));
		
	}
	
	@Test
	public void testMatchResourceID() {
		ResourceID a = new ResourceID("Workspace", "55").concatenate(new ResourceID("Calendar"));
		ResourceID b = new ResourceID("Workspace", "55").concatenate(new ResourceID("Calendar", "35957"));
		
		assertThat(a.matches(b), is(true));
	}
	
}
