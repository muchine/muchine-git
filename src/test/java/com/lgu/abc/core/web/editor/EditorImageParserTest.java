package com.lgu.abc.core.web.editor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import lombok.Data;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.file.MockImageUploader;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.infra.file.support.FileFinder;
import com.lgu.abc.core.web.editor.EditorImageParser;
import com.lgu.abc.core.web.editor.plugin.EditorImageUploadable;
import com.lgu.abc.test.common.base.BaseTest;

public class EditorImageParserTest extends BaseTest {

	@Autowired
	private MockImageUploader uploader;
	
	@Autowired
	private EditorImageParser parser;
	
	@Autowired
	private FileFinder finder;
		
	@Test
	public void testParseHtml() throws IOException {
		// Given
		String content = prepareContent();

		// When
		List<Image> images = parser.parse(content);
		
		// Then
		assertThat(images.size(), is(2));
		for (Image image : images) {
			logger.debug("image: " + image);
			assertThat(image.isTemp(), is(true));
		}				
	}
	
	@Test
	public void testHandleForCreation() throws Exception {
		// When
		List<Image> images = create().getImages();
		
		// Then
		for (Image image : images) {
			File found = finder.findById(image.getId());
			logger.debug("confirmed file: " + found);
			assertThat(found.isTemp(), is(false));
		}
	}
	
	@Test
	public void testHandleForUpdate() throws Exception {
		// Given
		Document document = create();
		Image newImage = upload();
		String newContent = document.getContent().replace(document.getImages().get(1).getUrl(), newImage.getUrl());
		
		// When
		parser.handle(newContent, getSession());
		
		// Then
		File found = finder.findById(document.getImages().get(0).getId());
		assertThat(found.isTemp(), is(false));
		
		found = finder.findById(newImage.getId());
		assertThat(found.isTemp(), is(false));
	}
		
	private Document create() {
		// Given
		String content = prepareContent();
		List<Image> images = parser.parse(content);		
		
		// When
		parser.handle(content, getSession());
		
		return new Document(content, images);
	}
	
	private Image upload() {
		Image uploaded = uploader.upload(getSession(), EditorImageUploadable.NAME);
		logger.debug("url: " + uploaded.getUrl());
		
		return uploaded;
	}
	
	private String prepareContent() {
		String content = getRawHtmlContent();
		content = content.replace("{1}", upload().getUrl());
		content = content.replace("{2}", upload().getUrl());
		
		return content;
	}
	
	private String getRawHtmlContent() {
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(getClass().getResourceAsStream("test.html"), writer);
			
			String content = writer.toString();
			logger.debug("content: " + content);
			
			return content;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private static @Data class Document {
		
		private final String content;
		
		private final List<Image> images;
		
		private Document(String content, List<Image> images) {
			this.content = content;
			this.images = images;
		}
	}
	
}
