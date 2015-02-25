package com.lgu.abc.core.prototype.org.user.support;

import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.prototype.org.department.Department;
import com.lgu.abc.core.prototype.org.department.support.DepartmentUtils;
import com.lgu.abc.core.prototype.org.user.User;

public class DepartmentContextUtils {

	public static List<Department> getAllDepartmentHierarchy(User user) {
		List<Department> departments = new ArrayList<Department>();
		List<Department> associated = user.departments();
		
		for (Department department : associated) {
			departments.addAll(department.hierarchy());
		}
		
		return departments;
	}
	
	public static List<Department> getMovedOutHierarchicalDepartments(User oldUser, User newUser) {
		List<Department> oldDepartments = getAllDepartmentHierarchy(oldUser);
		List<Department> newDepartments = getAllDepartmentHierarchy(newUser);
		
		return DepartmentUtils.getMovedOutDepartments(oldDepartments, newDepartments);
	}
	
	public static List<Department> getMovedOutDepartments(User oldUser, User newUser) {
		List<Department> oldDepartments = oldUser.departments();
		List<Department> newDepartments = newUser.departments();
		
		return DepartmentUtils.getMovedOutDepartments(oldDepartments, newDepartments);
	}
		
	public static List<Department> getMovedInHierarchicalDepartments(User oldUser, User newUser) {
		List<Department> oldDepartments = getAllDepartmentHierarchy(oldUser);
		List<Department> newDepartments = getAllDepartmentHierarchy(newUser);
		
		return DepartmentUtils.getMovedInDepartments(oldDepartments, newDepartments);
	}
	
	public static List<Department> getMovedInDepartments(User oldUser, User newUser) {
		List<Department> oldDepartments = oldUser.departments();
		List<Department> newDepartments = newUser.departments();
		
		return DepartmentUtils.getMovedInDepartments(oldDepartments, newDepartments);
	}
	
	public static List<Department> getUnchangedDepartments(User oldUser, User newUser) {
		List<Department> oldDepartments = oldUser.departments();
		List<Department> newDepartments = newUser.departments();
		
		return DepartmentUtils.getUnchangedDepartments(oldDepartments, newDepartments);
	}
	
	public static boolean isDepartmentChanged(User oldUser, User newUser) {
		List<Department> oldDepartments = oldUser.departments();
		List<Department> newDepartments = newUser.departments();
		
		return DepartmentUtils.isDepartmentChanged(oldDepartments, newDepartments);
	}
	
}
