package com.lgu.abc.core.prototype.org.user;

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
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.domain.DepartmentAssociation;

public class UserAuthoriziationTest {

	private Company company;
	
	private Department root;
	
	private Department child;
	
	private User owner;
	
	private User user;
	
	private ShareableEntity entity;
	
	@Before
	public void setup() {
		company = new Company("1");
		company.setName(Name.create("LG UPLUS"));
		
		root = new Department("1", company, Name.create("Root"));
				
		child = new Department("2", company, Name.create("Child"));
		child.setParent(root);
		
		user = new TestUser("1");
		user.setName(Name.create("Sejoon Lim"));
		
		user.addAssociation(new DepartmentAssociation(root));
		user.addAssociation(new DepartmentAssociation(child));
		
		user.selectDepartment(root.getId());
		
		entity = new ShareableActionEntity();
		
		owner = new TestUser("100");
		owner.setName(Name.create("Gildong Hong"));
		entity.setOwnable(owner);
	}
		
	/*
	 * User With Company Authority
	 */
	@Test
	public void testWithNoCompanyAuthority() {
		//		  						  N/A    NONE   READ  WRITE MANAGE
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}

	@Test
	public void testUserWithCompanyNone() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.NONE)));
		
		boolean[] read	 = new boolean[] {false, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testUserWithCompanyRead() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {false, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testUserWithCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {true, false, false, true, true};
		boolean[] manage = new boolean[] {false, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	@Test
	public void testUserWithCompanyManage() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.MANAGE)));
		
		boolean[] read	 = new boolean[] {true, false, true, true, true};
		boolean[] write	 = new boolean[] {true, false, false, true, true};
		boolean[] manage = new boolean[] {true, false, false, false, true};
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.testAllWithAccessLevel(read, write, manage);
	}
	
	/*
	 * User With Department And Company Authority
	 */
	@Test
	public void testWithNoDeptAndCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, true, false);
	}

	@Test
	public void testWithDeptNoneAndCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.NONE)));
				
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(false, false, false);
	}
	
	@Test
	public void testWithDeptReadAndCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.READ)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, false, false);
	}
	
	@Test
	public void testWithDeptWriteAndCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, true, false);
	}
	
	@Test
	public void testWithDeptManageAndCompanyWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.WRITE)));
		entity.addAccessor(new Accessor(root, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, true, true);
	}
	
	/*
	 * Root selected With Root Authority And Having Second Department Authority
	 */
	@Test
	public void testUserWithCompanyManageAndNoRootAndSecondWrite() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.MANAGE)));
		entity.addAccessor(new Accessor(child, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, true, true);
	}
	
	@Test
	public void testUserWithCompanyReadAndNoRootAndSecondManage() {
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(child, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, false, false);
	}
	
	/*
	 * Second selected With Root Authority And Having Second Department Authority
	 */
	@Test
	public void testSecondSelectedWithCompanyManageAndNoRootAndSecondWrite() {
		user.selectDepartment(child.getId());
		
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.MANAGE)));
		entity.addAccessor(new Accessor(child, new Privilege(AccessLevel.WRITE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, true, false);
	}
	
	@Test
	public void testSecondSelectedWithCompanyReadAndNoRootAndSecondManage() {
		user.selectDepartment(child.getId());
		
		entity.addAccessor(new Accessor(company, new Privilege(AccessLevel.READ)));
		entity.addAccessor(new Accessor(child, new Privilege(AccessLevel.MANAGE)));
		
		AccessLevelTester tester = new AccessLevelTester(user, entity);
		tester.assertAccessLevel(true, true, true);
	}
	
	private static class ShareableActionEntity extends ShareableEntity {
		@Override
		public Collection<Containable> containers() {
			Collection<Containable> containers = new ArrayList<Containable>();
			containers.add(this);
					
			return containers;
		}
	}
		
	private static final class TestUser extends User {

		public TestUser(String id) {
			super(id);
		}
		
		@Override
		public void load() {
			// remove loading from user registry for test.
		}
		
	}
	
}
