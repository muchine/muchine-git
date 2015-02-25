package com.lgu.abc.core.common.vo.comparator;

import com.lgu.abc.core.common.vo.Name;

public class JapaneseNameComparator extends AbstractNameComparator {

	private static final JapaneseNameComparator instance = new JapaneseNameComparator();
	
	private JapaneseNameComparator() {}
	
	public static JapaneseNameComparator instance() {
		return instance;
	}

	@Override
	protected String stringify(Name name) {
		return name.getJapanese();
	}

}
