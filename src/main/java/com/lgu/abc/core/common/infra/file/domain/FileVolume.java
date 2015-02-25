package com.lgu.abc.core.common.infra.file.domain;

import lombok.Data;

import com.lgu.abc.core.common.interfaces.Cleanable;
import com.lgu.abc.core.common.vo.Size;

public @Data class FileVolume implements Cleanable {
	
	private Size size;
	
	private boolean large;
	
	public FileVolume() {}
	
	public FileVolume(Size size, boolean large) {
		this.size = size;
		this.large = large;
	}
	
	@Override
	public void clean() {
		size = null;
		large = false;
	}
	
}
