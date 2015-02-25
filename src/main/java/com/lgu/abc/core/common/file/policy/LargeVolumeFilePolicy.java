package com.lgu.abc.core.common.file.policy;

import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.vo.Size;

public class LargeVolumeFilePolicy {

	public static boolean isLarge(FileUpload upload, Size threshold) {
		return upload.size().compareTo(threshold) > 0 ? true : upload.isLarge();
	}
	
}
