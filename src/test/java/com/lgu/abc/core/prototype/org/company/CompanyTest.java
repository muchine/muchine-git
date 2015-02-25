package com.lgu.abc.core.prototype.org.company;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.AccessLevelTester;
import com.lgu.abc.core.prototype.org.company.Company;

public class CompanyTest {
	
	private Company company;
	
	private ShareableEntity entity; 
	
	@Before
	public void setup() {
		company = new Company("1");
		company.setName(Name.create("LG UPLUS"));
		
		entity = new ShareableActionEntity();
	}

	@Test
	public void testCanRead() {
		AccessLevelTester tester = new AccessLevelTester(company, entity);
		tester.testReadWithAccessLevel(false, false, true, true, true);
	}
	
	@Test
	public void testCanWrite() {
		AccessLevelTester tester = new AccessLevelTester(company, entity);
		tester.testWriteWithAccessLevel(false, false, false, true, true);
	}

	@Test
	public void testCanManage() {
		AccessLevelTester tester = new AccessLevelTester(company, entity);
		tester.testManageWithAccessLevel(false, false, false, false, true);
	}
	
	@Test
	public void testAll() {
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(company, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	private static class ShareableActionEntity extends ShareableEntity {
		
		@Override
		public Collection<Containable> containers() {
			Collection<Containable> containers = new ArrayList<Containable>();
			containers.add(this);
			
			return containers;
		}
		
	}
	
}
