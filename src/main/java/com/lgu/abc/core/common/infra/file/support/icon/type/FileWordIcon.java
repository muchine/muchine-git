package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileWordIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"doc", "docx"
	};
	
	FileWordIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_doc";
	}

}
