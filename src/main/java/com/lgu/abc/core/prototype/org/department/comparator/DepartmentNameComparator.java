package com.lgu.abc.core.prototype.org.department.comparator;

import java.util.Comparator;

import com.lgu.abc.core.common.vo.comparator.KoreanNameComparator;
import com.lgu.abc.core.prototype.org.department.Department;

public class DepartmentNameComparator implements Comparator<Department> {

	private static final DepartmentNameComparator instance = new DepartmentNameComparator();
	
	private DepartmentNameComparator() {}
	
	@Override
	public int compare(Department o1, Department o2) {
		return KoreanNameComparator.instance().compare(o1.getName(), o2.getName());
	}
	
	public static DepartmentNameComparator instance() {
		return instance;
	}

}
