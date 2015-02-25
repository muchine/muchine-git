package com.lgu.abc.core.common.infra.file.domain.image;

import java.io.File;

import lombok.Data;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.file.image.resizer.ImageResizableFactory;
import com.lgu.abc.core.common.infra.file.Image;

public @Data class Thumbnail {
	
	private static final String SUFFIX = ".thumbnail.jpg";
	
	@JsonIgnore
	private final Image image;
	
	@JsonIgnore
	private final ImageSize size;
	
	/*
	 * NOTE: Do we need to make circular dependency between Image and Thumbnail class. More elegant solution is required. 
	 */
	public Thumbnail(Image image) {
		this(image, new ImageSize(110, 110));
	}
	
	public Thumbnail(Image image, ImageSize size) {
		Validate.notNull(image, "image for thumbnail is null.");
		this.image = image;
		this.size = size;
	}
	
	@JsonIgnore
	public String getPath() {
		return image.fullpath() + SUFFIX;
	}

	public String getUrl() {
		return image.getUrl() + SUFFIX;
	}
	
	public boolean exists() {
		return new File(getPath()).exists();
	}
	
	public void create() {
		ImageResizableFactory.generator().thumbnail(image, getPath(), size);
	}
	
	public void remove() {
		File thumbnail = new File(getPath());
		if (thumbnail.exists()) thumbnail.delete();
	}
	
}
