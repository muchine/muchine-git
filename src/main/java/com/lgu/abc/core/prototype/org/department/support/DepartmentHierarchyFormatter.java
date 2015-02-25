package com.lgu.abc.core.prototype.org.department.support;

import java.util.Collections;
import java.util.List;

import com.lgu.abc.core.prototype.org.department.Department;

public class DepartmentHierarchyFormatter {

	private static final String DELIMITER = " > ";
	
	private static final DepartmentHierarchyFormatter instance = new DepartmentHierarchyFormatter();
	
	private DepartmentHierarchyFormatter() {}
	
	public String breadcrum(Department department) {
		List<Department> hierarchy = department.hierarchy();
		Collections.reverse(hierarchy);
		
		StringBuilder builder = new StringBuilder();
		for (Department element : hierarchy) {
			if (builder.length() > 0) builder.append(DELIMITER);
			builder.append(element.getName().toString());
		}
		
		return builder.toString();
	}
	
	public static DepartmentHierarchyFormatter instance() {
		return instance;
	}
	
}
