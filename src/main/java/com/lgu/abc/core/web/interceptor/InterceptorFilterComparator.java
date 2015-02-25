package com.lgu.abc.core.web.interceptor;

import java.util.Comparator;

public class InterceptorFilterComparator implements Comparator<InterceptorFilter> {

	@Override
	public int compare(InterceptorFilter o1, InterceptorFilter o2) {
		return o1.order() - o2.order();
	}

}
