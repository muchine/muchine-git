package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileTextIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"txt"
	};
	
	FileTextIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_txt";
	}

}
