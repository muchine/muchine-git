package com.lgu.abc.core.common.infra.file.support.icon.type;

import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.common.infra.file.support.icon.FileIcon;

public class FileIconFactory {
	
	private static final List<FileIcon> icons = new ArrayList<FileIcon>();
	
	static {
		initialize();
	}
	
	public static FileIcon icon(String extension) {
		for (FileIcon icon : icons) {
			if (icon.accept(extension)) return icon;
		}
		
		return null;
	}
		
	private static void initialize() {
		icons.add(new FileExcelIcon());
		icons.add(new FileExeIcon());
		icons.add(new FileHtmlIcon());
		icons.add(new FileHwpIcon());
		icons.add(new FilePdfIcon());
		icons.add(new FilePictureIcon());
		icons.add(new FilePowerpointIcon());
		icons.add(new FileTextIcon());
		icons.add(new FileWordIcon());
		icons.add(new FileZipIcon());
		icons.add(new FileDefaultIcon());
	}
		
}
