package com.lgu.abc.core.prototype.org.department.support;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.utils.AggregateUtils;
import com.lgu.abc.core.prototype.org.department.Department;

public class DepartmentUtils {
	
	protected static final Log logger = LogFactory.getLog(DepartmentUtils.class);
	
	public static List<Department> getMovedOutDepartments(List<Department> oldDepartments, List<Department> newDepartments) {
		return AggregateUtils.getRemovedEntities(oldDepartments, newDepartments);
	}
	
	public static List<Department> getMovedInDepartments(List<Department> oldDepartments, List<Department> newDepartments) {
		return AggregateUtils.getAddedEntities(oldDepartments, newDepartments);
	}
	
	public static List<Department> getUnchangedDepartments(List<Department> oldDepartments, List<Department> newDepartments) {
		return AggregateUtils.getUnchangedEntities(oldDepartments, newDepartments);
	}
	
	public static boolean isDepartmentChanged(List<Department> oldDepartments, List<Department> newDepartments) {
		return AggregateUtils.isAggregateChanged(oldDepartments, newDepartments);
	}
	
	public static boolean contains(Department department, List<Department> collection) {
		return AggregateUtils.contains(department, collection);
	}
	
	public static Department find(Department query, List<Department> aggregate) {
		return AggregateUtils.find(query, aggregate);
	}
	
}
