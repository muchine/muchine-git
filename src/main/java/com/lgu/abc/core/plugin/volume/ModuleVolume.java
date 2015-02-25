package com.lgu.abc.core.plugin.volume;

import java.util.List;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.plugin.volume.report.UserVolumeReport;

public interface ModuleVolume extends ModulePluggable {

	List<UserVolumeReport> report();
		
}
