package com.lgu.abc.core.common.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.file.transfer.MultipartFileTransference;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.test.mock.MockFileGenerator;

@Component
public class MockFileUploader {
	
	@Autowired
	private FileManager manager;
	
	private final MockFileGenerator generator = new MockFileGenerator();
	
	public File upload(User session, String type) {
		return upload(session, type, false);		
	}
	
	public File upload(User session, String type, boolean large) {
		return upload(session, type, MockFileGenerator.FILE_NAME, large);
	}
	
	public File upload(User session, String type, String filename, boolean large) {
		try {
			final MockMultipartFile file = new MockMultipartFile("upload", filename, "jpg", generator.getInputStreamFrom(filename));
			File uploaded = manager.upload(type, new FileUpload(session, new MultipartFileTransference(file), large));
			return uploaded;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
