package com.lgu.abc.core.common.vo.comparator;

import com.lgu.abc.core.common.vo.Name;

public class KoreanNameComparator extends AbstractNameComparator {

	private static final KoreanNameComparator instance = new KoreanNameComparator();
	
	private KoreanNameComparator() {}
	
	public static KoreanNameComparator instance() {
		return instance;
	}

	@Override
	protected String stringify(Name name) {
		return name.getKorean();
	}

}
