package com.lgu.abc.core.common.file.image.resizer;

import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.infra.file.domain.image.ImageSize;

public interface ImageResizable {

	/**
	 * Create a thumb nail for a given image. A generated thumb nail image is stored in the destination.
	 * @param image the image for which a thumb nail is being created
	 * @param destination the destination in the file system to store a thumb nail image
	 * @param size the size of thumbn ail image
	 */
	void thumbnail(Image image, String destination, ImageSize size);

	/**
	 * Resize a given image. The resized image is stored in the same location. 
	 * @param image the image to resize
	 * @param size the size of new image
	 */
	void resize(Image image, ImageSize size);
	
}
