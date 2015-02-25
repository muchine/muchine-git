package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileHwpIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"hwp"
	};
	
	FileHwpIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_hwp";
	}

}
