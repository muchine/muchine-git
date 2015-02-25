package com.lgu.abc.core.base.repository.vo;

import lombok.Data;

public @Data class RearrangementPosition {

	private final int from;
	
	private final int to;

	RearrangementPosition(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
}
