package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FilePdfIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"pdf"
	};
	
	FilePdfIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_pdf";
	}

}
