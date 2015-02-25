package com.lgu.abc.core.common.infra.sequence;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences")
public @Data class Sequence {
	
	@Id
	private String id;
	
	private long sequence;
	
}
