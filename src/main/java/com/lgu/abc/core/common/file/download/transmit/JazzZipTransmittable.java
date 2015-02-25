package com.lgu.abc.core.common.file.download.transmit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import net.sf.jazzlib.ZipEntry;
import net.sf.jazzlib.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.lgu.abc.core.common.file.transfer.FileTransferable;

public class JazzZipTransmittable implements Transmittable {

	private final Collection<FileTransferable> transferables;
	
	public JazzZipTransmittable(Collection<FileTransferable> transferables) {
		this.transferables = transferables;
	}
	
	@Override
	public void transmit(OutputStream stream) throws IOException {
		ZipOutputStream out = new ZipOutputStream(stream);

		try {
			for (FileTransferable transferable : transferables) {
				ZipEntry entry = new ZipEntry(transferable.name());
				out.putNextEntry(entry);
				out.write(IOUtils.toByteArray(transferable.getInputStream()));
				out.closeEntry();
			}
		}
		finally {
			out.close();
		}
	}

}
