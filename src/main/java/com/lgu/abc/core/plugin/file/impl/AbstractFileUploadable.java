package com.lgu.abc.core.plugin.file.impl;

import com.lgu.abc.core.common.file.FileLocator;
import com.lgu.abc.core.common.file.policy.LargeVolumeFilePolicy;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;
import com.lgu.abc.core.plugin.file.FileClassifiable;
import com.lgu.abc.core.plugin.file.FileUploadable;
import com.lgu.abc.core.plugin.file.FileUploadableRegistry;

/**
 * An abstract implementation for attached files. 
 * @author Sejoon Lim
 * @since 2014. 2. 11.
 *
 */
public abstract class AbstractFileUploadable implements FileUploadable {

	private static final Size DEFAULT_THRESHOLD = new Size(20, Unit.MB);
	
	private static final FileClassifiable classifiable = new DailyFileClassifiable(); 
	
	protected AbstractFileUploadable(FileUploadableRegistry registry) {
		registry.add(this);
	}

	@Override
	public final String location(FileUpload upload) {
		return FileLocator.location(LargeVolumeFilePolicy.isLarge(upload, threshold())); 
	}

	@Override
	public final FileClassifiable classifiable() {
		return classifiable;
	}
	
	@Override
	public Size threshold() {
		return DEFAULT_THRESHOLD;
	}

	@Override
	public final File complete(File uploaded) {
		return uploaded;
	}
	
	@Override
	public abstract String name();
	
	@Override
	public abstract String path();
	
}
