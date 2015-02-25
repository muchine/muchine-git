package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FileExcelIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"xls", "xlsx"
	};
	
	FileExcelIcon() {
		super(extensions);
	}

	@Override
	public String cssClass() {
		return "file_xls";
	}

}
