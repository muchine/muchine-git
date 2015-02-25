package com.lgu.abc.core.common.infra.file.support.icon.type;

import com.lgu.abc.core.common.infra.file.support.icon.AbstractFileIcon;

public class FilePictureIcon extends AbstractFileIcon {

	private static final String[] extensions = new String[] {
		"gif", "jpg", "png", "bmp", "jpeg"
	};
	
	FilePictureIcon() {
		super(extensions);
	}
	
	@Override
	public String cssClass() {
		return "file_image";
	}

}
