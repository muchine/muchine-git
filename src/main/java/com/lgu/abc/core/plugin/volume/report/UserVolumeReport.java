package com.lgu.abc.core.plugin.volume.report;

import lombok.Data;

import com.lgu.abc.core.common.vo.Size;

public @Data class UserVolumeReport {

	private String userId;
	
	private Size usedVolume;
		
}
