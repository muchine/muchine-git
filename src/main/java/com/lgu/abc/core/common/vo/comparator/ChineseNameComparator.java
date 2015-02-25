package com.lgu.abc.core.common.vo.comparator;

import com.lgu.abc.core.common.vo.Name;

public class ChineseNameComparator extends AbstractNameComparator {

	private static final ChineseNameComparator instance = new ChineseNameComparator();
	
	private ChineseNameComparator() {}

	public static ChineseNameComparator instance() {
		return instance;
	}

	@Override
	protected String stringify(Name name) {
		return name.getChinese();
	}

}
