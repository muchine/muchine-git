package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileHtmlIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"html", "htm", "js", "css"
	};
	
	FileHtmlIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_html";
	}

}
