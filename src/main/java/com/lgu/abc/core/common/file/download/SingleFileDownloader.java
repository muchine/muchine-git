package com.lgu.abc.core.common.file.download;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;

import com.lgu.abc.core.common.file.download.transmit.FileTransmitter;
import com.lgu.abc.core.common.file.download.transmit.SingleFileTransmittable;
import com.lgu.abc.core.common.file.download.transmit.Transmittable;
import com.lgu.abc.core.common.file.transfer.FileTransferable;

public class SingleFileDownloader implements FileDownloadable {

	private final FileTransferable transferable;
	
	private final Transmittable transmittable;
	
	public SingleFileDownloader(FileTransferable transferable) {
		this.transferable = transferable;
		this.transmittable = new SingleFileTransmittable(transferable);
	}
	
	@Override
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileTransmitter transmitter = new FileTransmitter(response);
		
		transmitter.setFileName(transferable.name(), request);
		transmitter.setContentType(ContentType.APPLICATION_OCTET_STREAM);
		transmitter.transmitToResponse(transmittable);
	}

}
