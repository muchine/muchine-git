package com.lgu.abc.core.common.domain;

import com.lgu.abc.core.base.repository.vo.Rearrangement;

import lombok.Data;

public abstract @Data class AbstractRearrangement {

	private final Rearrangement rearrangement;
	
	protected AbstractRearrangement(Rearrangement rearrangement) {
		this.rearrangement = rearrangement;
	}
	
}
