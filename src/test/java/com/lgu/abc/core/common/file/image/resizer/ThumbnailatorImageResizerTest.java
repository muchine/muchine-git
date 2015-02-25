package com.lgu.abc.core.common.file.image.resizer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.file.MockImageUploader;
import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.infra.file.domain.image.ImageSize;
import com.lgu.abc.core.common.infra.file.domain.image.Thumbnail;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.mock.MockFileGenerator;
import com.lgu.abc.test.mock.file.MockImageUploadable;

public class ThumbnailatorImageResizerTest extends BaseTest {

	@Autowired
	private MockImageUploader uploader;
	
	@Test
	public void testCreate() {
		// Given
		Image image = uploader.upload(getSession(), MockImageUploadable.NAME, MockFileGenerator.IMAGE_NAME);
		ThumbnailatorImageResizer generator = new ThumbnailatorImageResizer();
		Thumbnail thumbnail = new Thumbnail(image);
				
		// When
		generator.thumbnail(thumbnail.getImage(), thumbnail.getPath(), new ImageSize(110, 110));
		
		// Then
		assertThat(new File(thumbnail.getPath()).exists(), is(true));
	}
	
}
