package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileZipIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"zip", "rar", "arz", "7z"
	};
	
	FileZipIcon() {
		super(extensions);
	}
	
	@Override
	public String cssClass() {
		return "file_zip";
	}

}
