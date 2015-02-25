package com.lgu.abc.core.common.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.prototype.org.user.User;

@Component
public class MockImageUploader {

	@Autowired
	private MockFileUploader uploader;
	
	public Image upload(User session, String type) {
		return new Image(uploader.upload(session, type));
	}
	
	public Image upload(User session, String type, String filename) {
		return new Image(uploader.upload(session, type, filename, false));
	}
	
}
