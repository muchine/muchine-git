package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileExeIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"exe"
	};
	
	FileExeIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_exe";
	}

}
