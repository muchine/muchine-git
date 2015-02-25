package com.lgu.abc.core.web.editor.plugin;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.file.MockFileUploader;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.web.editor.plugin.EditorImageUploadable;
import com.lgu.abc.test.common.base.BaseTest;

public class EditorImageUploadableTest extends BaseTest {

	@Autowired
	private MockFileUploader uploader;
	
	@Test
	public void testUpload() {
		File uploaded = uploader.upload(getSession(), EditorImageUploadable.NAME);
		logger.debug("uploaded: " + uploaded);
		
		new java.io.File(uploaded.fullpath()).delete();
	}
	
}
