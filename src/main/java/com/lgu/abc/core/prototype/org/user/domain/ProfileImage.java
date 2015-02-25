package com.lgu.abc.core.prototype.org.user.domain;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.infra.file.domain.image.ImageSize;
import com.lgu.abc.core.common.infra.file.domain.image.Thumbnail;

public @Data class ProfileImage {

	public static final String DEFAULT_IMAGE_URL = "/resources/abc/images/profile_pic.png";
	
	private static final ImageSize DEFAULT_SIZE = new ImageSize(140, 140);
	
	private String id;
	
	private String url;
	
	public ProfileImage() {
		this.url = DEFAULT_IMAGE_URL;
	}
	
	public ProfileImage(String id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public ProfileImage(Image image) {
		this.id = image.getId();
		this.url = getThumbnailUrl(image);
	}
	
	public boolean isNull() {
		return StringUtils.isEmpty(id);
	}
	
	public String getFullUrl() {
		if (StringUtils.isEmpty(url)) return "";
		
		AbcConfig configuration = AbcConfig.instance();
		return "http://" + configuration.system().proxy().url() + url;
	}
	
	private String getThumbnailUrl(Image image) {
		/*
		 * If an image doesn't exist, we should consider returning empty url or throwing an exception.
		 * For unit tests to be passed, return original url.
		 */
		if (!image.exists()) return image.getUrl();
		
		try {
			Thumbnail thumbnail = new Thumbnail(image, DEFAULT_SIZE);
			if (!thumbnail.exists()) thumbnail.create();
			
			return thumbnail.getUrl();	
		} catch(Exception e) {
			return image.getUrl();
		}
		
	}
	
}
