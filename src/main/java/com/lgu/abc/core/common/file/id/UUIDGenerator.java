package com.lgu.abc.core.common.file.id;

import java.util.UUID;

public class UUIDGenerator implements FileIdGeneratable {

	private static final UUIDGenerator instance = new UUIDGenerator();
	
	private UUIDGenerator() {}
	
	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}
	
	public static UUIDGenerator instance() {
		return instance;
	}

}
