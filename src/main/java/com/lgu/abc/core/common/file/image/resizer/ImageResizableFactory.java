package com.lgu.abc.core.common.file.image.resizer;

public class ImageResizableFactory {

	private static final ImageResizable generator = new ThumbnailatorImageResizer();
	
	public static ImageResizable generator() {
		return generator;
	}
	
}
