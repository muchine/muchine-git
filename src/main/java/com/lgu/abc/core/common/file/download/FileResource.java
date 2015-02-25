package com.lgu.abc.core.common.file.download;

import com.lgu.abc.core.common.file.transfer.FileTransferable;
import com.lgu.abc.core.common.file.transfer.JavaFileTransference;
import com.lgu.abc.core.common.infra.file.File;

public class FileResource {

	private final File file;
	
	private final java.io.File content;
	
	public FileResource(File file, java.io.File content) {
		this.file = file;
		this.content = content;
	}
	
	public File file() {
		return file;
	}
	
	public java.io.File content() {
		return content;
	}
	
	public boolean exists() {
		return file != null && hasContent();
	}
	
	public boolean hasContent() {
		return content != null && content.exists();
	}
	
	public FileTransferable transferable() {
		return new JavaFileTransference(file.name(), content);
	}
			
}
