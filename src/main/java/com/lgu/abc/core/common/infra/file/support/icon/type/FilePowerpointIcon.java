package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FilePowerpointIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"ppt", "pptx"
	};
	
	FilePowerpointIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_ppt";
	}

}
