package com.lgu.abc.core.common.infra.file;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.file.MockImageUploader;
import com.lgu.abc.core.common.infra.file.domain.image.ImageSize;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.mock.file.MockImageUploadable;

public class ImageTest extends BaseTest {

	@Autowired
	private MockImageUploader uploader;
	
	@Test
	public void testSize() {
		// Given
		Photo image = new Photo(uploader.upload(getSession(), MockImageUploadable.NAME));
		
		// When
		ImageSize size = image.getSize();
		
		// Then
		logger.debug("size: " + size);
		assertTrue(size.getWidth() > 0);
		assertTrue(size.getHeight() > 0);
	}
	
}
