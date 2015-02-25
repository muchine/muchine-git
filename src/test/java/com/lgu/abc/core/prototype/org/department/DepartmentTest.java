package com.lgu.abc.core.prototype.org.department;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.AccessLevelTester;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;

public class DepartmentTest {
	
	private Company company;
	
	private Department root;
	
	private Department child;
	
	private ShareableEntity entity;
	
	@Before
	public void setup() {
		entity = new ShareableActionEntity();
		
		company = new Company("1");
		company.setName(Name.create("LG UPLUS"));
		
		root = new Department("1", company, Name.create("Root"));
				
		child = new Department("2", company, Name.create("Child"));
		child.setParent(root);
	}

	/*
	 * Root
	 */
	@Test
	public void testWithNoCompanyAuthority() {
		//								  N/A    NONE   READ  WRITE MANAGE
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(root, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
		
	@Test
	public void testRootWithCompanyNone() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(root, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testRootWithCompanyRead() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(root, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testRootWithCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {true, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(root, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testRootWithCompanyManage() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.MANAGE)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {true, false, false, true, true};
		boolean[] manage = new boolean[] {true, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(root, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	/*
	 * Child With No Parent Authority And Having Company Authority
	 */
	@Test
	public void testChildWithNoCompanyAuthority() {
		//								  N/A    NONE   READ  WRITE MANAGE
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
		
	@Test
	public void testChildWithdCompanyNone() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testChildWithCompanyRead() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testChildWithCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {true, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testChildWithCompanyManage() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.MANAGE)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {true, false, false, true, true};
		boolean[] manage = new boolean[] {true, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	/*
	 * Child With Having Parent Authority And No Company Authority
	 */
	@Test
	public void testChildWithParentNoneAndNoCompanyAuthority() {
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.NONE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(false, false, false);
	}
	
	@Test
	public void testChildWithParentReadAndNoCompanyAuthority() {
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.READ)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, false, false);
	}
	
	@Test
	public void testChildWithParentWriteAndNoCompanyAuthority() {
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, false);
	}
	
	@Test
	public void testChildWithParentManageAndNoCompanyAuthority() {
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, true);
	}
	
	/*
	 * Child With Having Parent Authority And None Company Authority
	 */
	@Test
	public void testChildWithParentNoneAndNoneCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.NONE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(false, false, false);
	}
	
	@Test
	public void testChildWithParentReadAndNoneCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.READ)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, false, false);
	}
	
	@Test
	public void testChildWithParentWriteAndNoneCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, false);
	}
	
	@Test
	public void testChildWithParentManageAndNoneCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, true);
	}
		
	/*
	 * Child With Having Parent Authority And Read Company Authority
	 */
	@Test
	public void testChildWithParentNoneAndReadCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.NONE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(false, false, false);
	}
	
	@Test
	public void testChildWithParentReadAndReadCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.READ)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, false, false);
	}
	
	@Test
	public void testChildWithParentWriteAndReadCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, false);
	}
	
	@Test
	public void testChildWithParentManageAndReadCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, true);
	}
	
	/*
	 * Child With Having Parent Authority And Write Company Authority
	 */
	@Test
	public void testChildWithParentNoneAndWriteCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.NONE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(false, false, false);
	}
	
	@Test
	public void testChildWithParentReadAndWriteCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.READ)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, false, false);
	}
	
	@Test
	public void testChildWithParentWriteAndWriteCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, false);
	}
	
	@Test
	public void testChildWithParentManageAndWriteCompanyAuthority() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, true, true);
	}
	
	@Test
	public void testChildWithParentReadAndCompanyManage() {
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.MANAGE)));
				
		AccessLevelTester tester = new AccessLevelTester(child, entity);
		tester.assertAccessLevel(true, false, false);
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
