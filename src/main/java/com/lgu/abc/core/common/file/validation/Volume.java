package com.lgu.abc.core.common.file.validation;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Data;

import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.support.annotation.Domain;

@Domain
public @Data class Volume {
	
	/*
	 * This value is not used now but kept for requirement change.
	 */
	@Null @Max(100)
	private Integer attachedFileNumber;
	
	@NotNull @Valid
	private Size attachedFileSize;
	
	@NotNull @Valid
	private Size totalAttachedFileSize;
	
	@NotNull @Valid
	private Size totalFileSize;
	
	@Null
	private Size usedFileSize;
		
	public Size getAvailableFileSize() {
		return totalFileSize.subtract(usedFileSize);
	}
	
	public boolean isVolumeAvailable() {
		return this.getAvailableFileSize().bytes() > 0;
	}
	
	public void update(Volume entity) {
		setUsedFileSize(entity.getUsedFileSize());
	}
	
	public static Volume NULL() {
		Volume volume = new Volume();
		
		volume.attachedFileNumber = 0;
		volume.attachedFileSize = new Size(0);
		volume.totalAttachedFileSize = new Size(0);
		volume.totalFileSize = new Size(0);
		volume.usedFileSize = new Size(0);
		
		return volume;
	}
	
}
