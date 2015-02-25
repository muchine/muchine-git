package com.lgu.abc.core.common.infra.file;

import com.lgu.abc.core.common.infra.file.domain.image.ImageSize;
import com.lgu.abc.core.common.infra.file.domain.image.Thumbnail;

public class Photo extends Image {

	private ImageSize size = new ImageSize();
	
	public Photo() {}
	
	public Photo(File file) {
		super(file);
	}
	
	public Photo(Image image) {
		super(image);
	}
	
	public Photo(Photo photo) {
		super(photo);
		this.size = photo.size;
	}
	
	public ImageSize getSize() {
		size.initialize(fullpath());
		return size;
	}
	
	public Thumbnail getThumbnail() {
		return new Thumbnail(this);
	}
	
	public Thumbnail getThumbnail(ImageSize size) {
		return new Thumbnail(this, size);
	}

	@Override
	public void confirm() {
		super.confirm();
		createThumbnail();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		invalidateThumbnail();
	}
	
	private void createThumbnail() {
		getThumbnail().create();
	}
	
	private void invalidateThumbnail() {
		getThumbnail().remove();
	}
	
}
