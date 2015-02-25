package com.lgu.abc.core.common.file.transfer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.vo.Size;

/**
 * Byte array implementation for file transfer to a permanent storage.
 * @author Sejoon Lim
 * @since 2014. 2. 5.
 *
 */
public class ByteArrayTransference implements FileTransferable {

	private final String name;
	
	private final byte[] bytes;
	
	public ByteArrayTransference(String name, byte[] bytes) {
		Validate.isTrue(!StringUtils.isEmpty(name), "name is empty.");
		Validate.isTrue(bytes != null && bytes.length > 0, "byte array is empty");
		
		this.name = name;
		this.bytes = bytes;
	}
	
	public ByteArrayTransference(String name, InputStream stream) throws IOException {
		this(name, IOUtils.toByteArray(stream));
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public Size size() {
		return new Size(bytes.length);
	}

	@Override
	public void transferTo(File destination) throws FileTransferException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(destination);
			out.write(bytes);
			out.close();
		} catch (IOException e) {
			if (out != null) try { out.close(); } catch (IOException ie) {}
			throw new FileTransferException(e);
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}

}
