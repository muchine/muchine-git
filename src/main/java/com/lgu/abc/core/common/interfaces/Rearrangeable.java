package com.lgu.abc.core.common.interfaces;

import com.lgu.abc.core.common.domain.AbstractRearrangement;

public interface Rearrangeable {
	
	/*
	 * This value is assigned considering that max value of MS-SQL smallint is 32,767.
	 */
	public static final int MAX_ORDER = 32000;

	int order();
	
	void order(int order);
	
	AbstractRearrangement rearrange(int to);
	
}
