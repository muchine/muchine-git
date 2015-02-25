package com.lgu.abc.core.common.file.transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.vo.Size;

/**
 * Java file implementation for file transfer to a permanent storage.
 * @author Sejoon Lim
 * @since 2014. 2. 5.
 *
 */
public class JavaFileTransference implements FileTransferable {
	
	private final String name;
	
	private final File file;
	
	public JavaFileTransference(String name, File file) {
		this.name = name;
		this.file = file;
	}
	
	public JavaFileTransference(String name, String filepath) {
		Validate.isTrue(!StringUtils.isEmpty(name), "name is empty.");
		Validate.notNull(!StringUtils.isEmpty(filepath), "file is empty.");
		
		this.name = name;
		this.file = new File(filepath);
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public Size size() {
		return new Size(file.length());
	}

	@Override
	public void transferTo(File destination) throws FileTransferException {
		try {
			FileUtils.copyFile(file, destination);
		} catch (IOException e) {
			throw new FileTransferException(e);
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

}
