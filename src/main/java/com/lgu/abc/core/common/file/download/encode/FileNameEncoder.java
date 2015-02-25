package com.lgu.abc.core.common.file.download.encode;

import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.common.file.download.encode.exception.UnsupportedFileNameEncodingException;
import com.lgu.abc.core.web.browser.BrowserSpecification;

public class FileNameEncoder implements FileNameEncodable {

	private static final List<FileNameEncodable> encodables = new ArrayList<FileNameEncodable>();
	
	static {
		initialize();
	}
	
	private static void initialize() {
		encodables.add(new IEFileNameEncoder());
		encodables.add(new NonIEFileNameEncoder());
	}
	
	@Override
	public String encode(String filename, BrowserSpecification browser) throws UnsupportedFileNameEncodingException {
		for (FileNameEncodable encodable : encodables) {
			if (encodable.canSupport(browser)) return encodable.encode(filename, browser);
		}
		
		throw new UnsupportedFileNameEncodingException();
	}

	@Override
	public boolean canSupport(BrowserSpecification browser) {
		for (FileNameEncodable encodable : encodables) {
			if (encodable.canSupport(browser)) return true;
		}
		
		return false;
	}

}
