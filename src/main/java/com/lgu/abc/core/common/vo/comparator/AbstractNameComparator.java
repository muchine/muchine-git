package com.lgu.abc.core.common.vo.comparator;

import java.util.Comparator;

import com.lgu.abc.core.common.vo.Name;

public abstract class AbstractNameComparator implements Comparator<Name> {

	@Override
	public final int compare(Name o1, Name o2) {
		Integer tested = testNull(o1, o2);
		if (tested != null) return tested.intValue();
		
		String n1 = stringify(o1);
		String n2 = stringify(o2);
		
		tested = testNull(n1, n2);
		if (tested != null) return tested.intValue();
		
		return stringify(o1).compareTo(stringify(o2));
	}
	
	protected abstract String stringify(Name name);
	
	private Integer testNull(Object o1, Object o2) {
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return 1;
		if (o2 == null) return -1;
		
		return null;
	}

}
