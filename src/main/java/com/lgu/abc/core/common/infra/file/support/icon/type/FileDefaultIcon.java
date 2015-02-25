package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.FileIcon;

public class FileDefaultIcon implements FileIcon {

	@Override
	public String cssClass() {
		return "file_ect";
	}

	@Override
	public boolean accept(String extension) {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(getClass());
	}

}
