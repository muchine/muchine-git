package com.lgu.abc.core.common.vo.comparator;

import com.lgu.abc.core.common.vo.Name;

public class EnglishNameComparator extends AbstractNameComparator {

	private static final EnglishNameComparator instance = new EnglishNameComparator();
	
	private EnglishNameComparator() {}
		
	public static EnglishNameComparator instance() {
		return instance;
	}

	@Override
	protected String stringify(Name name) {
		return name.getEnglish();
	}

}
