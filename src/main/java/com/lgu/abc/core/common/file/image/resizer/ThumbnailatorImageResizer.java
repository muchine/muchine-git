package com.lgu.abc.core.common.file.image.resizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.file.image.exception.ImageResizingFailureException;
import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.infra.file.domain.image.ImageSize;

public class ThumbnailatorImageResizer implements ImageResizable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void thumbnail(Image image, String name, ImageSize size) {
		create(image.fullpath(), name, size);
	}
	
	@Override
	public void resize(Image image, ImageSize size) {
		resize(image.fullpath(), size);
	}
	
	public void create(String imagepath, String destination, ImageSize size) {
		try {
			logger.debug("create thumbnail... " + imagepath);
			BufferedImage buffered = ImageIO.read(new File(imagepath));
			ImageSize thumbnailSize = computeThumbnailSize(buffered, size);
			
			save(buffered, destination, thumbnailSize);
		} catch (IOException e) {
			throw new ImageResizingFailureException(e);
		}
	}
	
	public void resize(String imagepath, ImageSize size) {
		try {
			logger.debug("resize image... " + imagepath);
			BufferedImage buffered = ImageIO.read(new File(imagepath));
			if (!canResize(buffered, size)) return;
			
			save(buffered, imagepath, size);
		} catch (IOException e) {
			throw new ImageResizingFailureException(e);
		}
	}
	
	private void save(BufferedImage image, String destination, ImageSize size) throws IOException {
		logger.debug("resizing and saving images to destination: " + destination);
		FileOutputStream stream = new FileOutputStream(new File(destination));
		
		Thumbnails.of(image)
				.size(size.getWidth(), size.getHeight())
				.outputFormat("jpg")
				.toOutputStream(stream);
	}
	
	private ImageSize computeThumbnailSize(BufferedImage image, ImageSize size) {
		ImageSize currentSize = getBufferedImageSize(image);
		return currentSize.compareTo(size) > 0 ? size : currentSize; 
	}
	
	private boolean canResize(BufferedImage image, ImageSize size) {
		ImageSize currentSize = getBufferedImageSize(image);
		logger.debug("current: " + currentSize + ", given: " + size);
		return currentSize.getWidth() > size.getWidth() || currentSize.getHeight() > size.getHeight();
	}
	
	private ImageSize getBufferedImageSize(BufferedImage image) {
		return new ImageSize(image.getWidth(), image.getHeight());
	}
	
	public static void main(String[] args) {
		ThumbnailatorImageResizer generator = new ThumbnailatorImageResizer();
		generator.resize("D:/abc/image/pht/20140716/c1c83b6e-85bc-4e52-8a8e-ba217cac0433", new ImageSize(110, 110));
	}
	
}
