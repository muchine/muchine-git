package com.lgu.abc.core.common.file.id;

public class DirectIdGenerator implements FileIdGeneratable {

	private final String id;
	
	public DirectIdGenerator(String id) {
		this.id = id;
	}
	
	@Override
	public String generate() {
		return id;
	}

}
