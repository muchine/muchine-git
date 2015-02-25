package com.lgu.abc.core.common.file.transfer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.vo.Size;

/**
 * An MuptipartFile implementation for file transference. This class transfers a file content in MultipartFile object
 * to a permanent file storage.
 * @author Sejoon Lim
 * @since 2014.02.25
 * 
 */
public class MultipartFileTransference implements FileTransferable {

	private final MultipartFile file;
	
	public MultipartFileTransference(MultipartFile file) {
		this.file = file;
	}
	
	@Override
	public String name() {
		return file.getOriginalFilename();
	}

	@Override
	public Size size() {
		return new Size(file.getSize());
	}

	/**
	 * Delegates file transfer to MultipartFile class so this method has all constraints given by the delegated class.  
	 */
	@Override
	public void transferTo(File destination) throws FileTransferException {
		try {
			file.transferTo(destination);
		} 
		catch (Exception e) {
			throw new FileTransferException(e);
		}		
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return file.getInputStream();
	}

}
