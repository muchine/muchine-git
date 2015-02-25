package com.lgu.abc.core.plugin.file.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.configuration.upload.image.ImageLocationEnvironment;
import com.lgu.abc.core.common.file.policy.LargeVolumeFilePolicy;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;
import com.lgu.abc.core.plugin.file.FileClassifiable;
import com.lgu.abc.core.plugin.file.FileUploadable;
import com.lgu.abc.core.plugin.file.FileUploadableRegistry;

/**
 * An abstract implementation to upload image file.
 * @author Sejoon Lim
 * @since 2014. 3. 7.
 *
 */
public abstract class AbstractImageUploadable implements FileUploadable {

	private static final Size DEFAULT_THRESHOLD = new Size(20, Unit.MB);
	
	private static final FileClassifiable classifiable = new DailyFileClassifiable(); 
	
	@Autowired
	private AbcConfig configuration;
	
	protected AbstractImageUploadable(FileUploadableRegistry registry) {
		registry.add(this);
	}

	@Override
	public final String location(FileUpload upload) {
		ImageLocationEnvironment location = configuration.upload().image().location(); 
		return LargeVolumeFilePolicy.isLarge(upload, threshold()) ? location.large() : location.permanent();
	}

	@Override
	public FileClassifiable classifiable() {
		return classifiable;
	}
	
	@Override
	public Size threshold() {
		return DEFAULT_THRESHOLD;
	}

	@Override
	public File complete(File uploaded) {
		return new Image(uploaded);
	}
	
	@Override
	public abstract String name();
	
	@Override
	public abstract String path();
	
}
