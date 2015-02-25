package com.lgu.abc.core.common.file.transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.util.IOUtils;

import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.vo.Size;

public class InputStreamTransference implements FileTransferable {

	private final String name;
	
	private final Size size;
	
	private final InputStream in;
	
	public InputStreamTransference(String name, Size size, InputStream in) {
		this.name = name;
		this.size = size;
		this.in = in;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public Size size() {
		return size;
	}

	@Override
	public void transferTo(File destination) throws FileTransferException {
		FileOutputStream out;
		try {
			out = new FileOutputStream(destination);
			IOUtils.copy(in, out);
		} catch (FileNotFoundException e) {
			throw new FileTransferException(e);
		} catch (IOException e) {
			throw new FileTransferException(e);
		}		
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return in;
	}

}
