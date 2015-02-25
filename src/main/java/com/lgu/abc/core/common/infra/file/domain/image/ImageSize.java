package com.lgu.abc.core.common.infra.file.domain.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public @Data class ImageSize implements Comparable<ImageSize> {
	
	@JsonIgnore
	protected final Log logger = LogFactory.getLog(getClass());
	
	@JsonIgnore
	private boolean loaded = false;
	
	private int width;
	
	private int height;
	
	public ImageSize() {}
	
	public ImageSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void initialize(String filepath) {
		if (loaded) return;
		
		try {
			File file = new File(filepath);
			if (!file.exists()) return;
			
			BufferedImage image = ImageIO.read(file);
			width = image.getWidth();
			height = image.getHeight();
			
			loaded = true;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public int area() {
		return width * height;
	}

	@Override
	public int compareTo(ImageSize o) {
		return this.area() - o.area();
	}
	
}
