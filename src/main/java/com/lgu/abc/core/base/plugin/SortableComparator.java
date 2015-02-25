package com.lgu.abc.core.base.plugin;

import java.util.Comparator;

public class SortableComparator implements Comparator<Sortable> {

	private static final SortableComparator instance = new SortableComparator();
	
	private SortableComparator() {}
	
	@Override
	public int compare(Sortable o1, Sortable o2) {
		return o1.order() - o2.order();
	}
	
	public static SortableComparator instance() {
		return instance;
	}

}
