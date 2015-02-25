package com.lgu.abc.core.plugin.volume;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.PluggableUtils;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class ModuleVolumeRegistry {

	private static final Set<ModuleVolume> volumes = new HashSet<ModuleVolume>();
	
	public synchronized void add(ModuleVolume volume) {
		PluggableUtils.add(volume, volumes);
	}
	
	public static ModuleVolume find(String volumeId) {
		for (ModuleVolume volume : all()) {
			if (volume.id().equals(volumeId)) return volume;
		}
		
		return null;
	}
	
	public static List<ModuleVolume> enabled(Company company) {
		return PluggableUtils.enabled(company, volumes);
	}
	
	public static Set<ModuleVolume> all() {
		return PluggableUtils.all(volumes);
	}
	
}
