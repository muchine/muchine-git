package com.lgu.abc.core.common.file.download.transmit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import com.lgu.abc.core.common.file.transfer.FileTransferable;

/**
 * Compress and transmit multiple files to output stream using apache common compress library
 * @author Sejoon Lim
 * @since 2014. 4. 15.
 *
 */
public class CompressedZipTransmittable implements Transmittable {

	private static final String DEFAULT_ENCODING = "UTF8";
	
	private final Collection<FileTransferable> transferables;
	
	private final String encoding;
	
	public CompressedZipTransmittable(Collection<FileTransferable> transferables) {
		this(transferables, DEFAULT_ENCODING);
	}
	
	public CompressedZipTransmittable(Collection<FileTransferable> transferables, String encoding) {
		this.transferables = transferables;
		this.encoding = encoding;
	} 
	
	@Override
	public void transmit(OutputStream stream) throws IOException {
		ZipArchiveOutputStream out = new ZipArchiveOutputStream(stream);
		out.setEncoding(encoding);
		
		try {
			for (FileTransferable transferable : transferables) {
				ZipArchiveEntry entry = createEntry(transferable);
				
				out.putArchiveEntry(entry);
				out.write(IOUtils.toByteArray(transferable.getInputStream()));
				out.closeArchiveEntry();
			}
		} catch (UnsupportedEncodingException e) {
			throw new IOException(e);
		}
		finally {
			out.close();
		}
	}
	
	private ZipArchiveEntry createEntry(FileTransferable transferable) throws UnsupportedEncodingException {
		String encodedName = encodeName(transferable.name());
		ZipArchiveEntry entry = new ZipArchiveEntry(encodedName);
		entry.setSize(transferable.size().bytes());
		
		return entry;
	}
	
	private String encodeName(String name) throws UnsupportedEncodingException {
		return DEFAULT_ENCODING.equals(encoding) ? name : new String(name.getBytes(), encoding);
	}

}
