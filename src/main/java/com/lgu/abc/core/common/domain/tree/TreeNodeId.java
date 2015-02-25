package com.lgu.abc.core.common.domain.tree;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public @Data class TreeNodeId {

	@JsonProperty("key")
	private final String id;
	
	@JsonProperty("type")
	private final String type;
	
	public TreeNodeId(String id, String type) {
		this.id = id;
		this.type = type;
	}
	
}
