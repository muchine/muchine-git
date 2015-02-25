package com.lgu.abc.core.prototype.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lombok.Data;

import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.domain.DepartmentAssociation;
import com.lgu.abc.core.prototype.org.user.domain.Position;
import com.lgu.abc.core.prototype.org.user.domain.Title;
import com.lgu.abc.test.common.base.fixture.SessionFactory;

public @Data class OrganizationGenerator {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String COMPANY_NAME = "LG UPLUS";
	
	private static final String VIRTUAL_DEPARTMENT_NAME = "VIRTUAL";
	private static final String ROOT1_DEPARTMENT_NAME = "ROOT #1";
	private static final String ROOT2_DEPARTMENT_NAME = "ROOT #2";
	private static final String CHILD_DEPARTMENT_NAME = "CHILD #1";
	
	private Company company;
	
	private Department d0;
	
	private Department d1;
	
	private Department d2;
	
	private Department d3;
	
	private User u1;
	
	private User u2;
	
	private User u3;
	
	private User u4;
	
	public OrganizationGenerator() {
		generate();
	}
	
	private void generate() {
		company = new Company(SessionFactory.COMPANY_ID, Name.create(COMPANY_NAME));
		
		d0 = new Department("09", company, Name.create(VIRTUAL_DEPARTMENT_NAME));
		d0.setVirtual(true);
		
		d1 = new Department("10", company, Name.create(ROOT1_DEPARTMENT_NAME));
		d2 = new Department("11", company, Name.create(ROOT2_DEPARTMENT_NAME));
		d3 = new Department("12", company, Name.create(CHILD_DEPARTMENT_NAME));
		d3.setParent(d1);
		
		u1 = create("100", d1, "admin", position("manager", 3), "leader", 1);
		u1.setActive(true);
		
		u2 = create("101", d2, "test1", position("president", 1), "ceo", 2);
		u2.setActive(false);
		
		u3 = create("102", d3, "test2", position("vice president", 2), "team manager", 4);
		u3.setActive(true);
		
		u4 = create("103", d3, "test3", position("assistant", 4), null, 3);
		u4.setActive(true);
	}
	
	private User create(String id, Department department, String name, Position position, String title, int order) {
		User user = new User(id);
		user.addAssociation(new DepartmentAssociation(department, true, order));
		
		user.setName(Name.create(name));
		if (position != null) user.setPosition(position);
		if (title != null) user.setTitle(new Title(title));
		
		return user;
	}
	
	private Position position(String name, int order) {
		return new Position(Name.create(name), order);
	}
	
	public List<Department> departments() {
		List<Department> departments = new ArrayList<Department>();
		departments.add(d0);
		departments.add(d1);
		departments.add(d2);
		departments.add(d3);
		
		return departments;
	}
	
	public List<User> users() {
		List<User> users = new ArrayList<User>();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		
		return users;
	}
	
}
