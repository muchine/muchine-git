package com.lgu.abc.core.common.file.download.transmit;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.lgu.abc.core.common.file.transfer.FileTransferable;

public class SingleFileTransmittable implements Transmittable {

	private final FileTransferable transferable;
	
	public SingleFileTransmittable(FileTransferable transferable) {
		this.transferable = transferable;
	}
	
	@Override
	public void transmit(OutputStream stream) throws IOException {
		IOUtils.copy(transferable.getInputStream(), stream);
	}

}
