package com.lgu.abc.core.common.file.download;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface FileDownloadable {

	void download(HttpServletRequest request, HttpServletResponse response)  throws IOException;
	
}
