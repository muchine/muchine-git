package com.lgu.abc.core.common.file.download;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.apache.http.entity.ContentType;

import com.lgu.abc.core.common.file.download.transmit.FileTransmitter;
import com.lgu.abc.core.common.file.download.transmit.Transmittable;
import com.lgu.abc.core.common.file.download.transmit.CompressedZipTransmittable;
import com.lgu.abc.core.common.file.transfer.FileTransferable;

public class CompressedFileDownloader implements FileDownloadable {

	private final String filename;
		
	private final Transmittable transmittable;
	
	public CompressedFileDownloader(String filename, Collection<FileTransferable> transferables) {
		this(filename, transferables, "UTF8");
	}
	
	public CompressedFileDownloader(String filename, Collection<FileTransferable> transferables, String encoding) {
		Validate.notEmpty(transferables, "transferables collection is empty.");
		
		this.filename = filename;
		this.transmittable = new CompressedZipTransmittable(transferables, encoding);
	}
	
	@Override
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileTransmitter transmitter = new FileTransmitter(response);
		transmitter.setFileName(filename, request);
		transmitter.setContentType(ContentType.APPLICATION_OCTET_STREAM);
		transmitter.transmitToResponse(transmittable);
	}

}
