package com.lgu.abc.core.common.file.download.transmit;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;

import com.lgu.abc.core.common.file.download.encode.FileNameEncodable;
import com.lgu.abc.core.common.file.download.encode.FileNameEncoder;
import com.lgu.abc.core.web.support.WebUtils;

public class FileTransmitter {

	private static final FileNameEncodable encodable = new FileNameEncoder();
	
	private final HttpServletResponse response;
	
	public FileTransmitter(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setContentType(String type) {
		response.setContentType(type);
	}
	
	public void setContentType(ContentType type) {
		response.setContentType(type.getMimeType());
	}
	
	public void setFileName(String filename, HttpServletRequest request) {
		final String encoded = encodable.encode(filename, WebUtils.browser(request));
		response.setHeader("Content-Disposition","attachment; filename=\"" + encoded + "\"");
	}
	
	public void transmitToResponse(Transmittable transmittable) throws IOException {
		OutputStream stream = response.getOutputStream();
		transmittable.transmit(stream);		
	}
	
}
