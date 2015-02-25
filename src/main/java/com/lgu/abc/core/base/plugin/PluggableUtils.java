package com.lgu.abc.core.base.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.selector.ModuleSelectable;
import com.lgu.abc.core.module.selector.status.EnabledModuleSelector;
import com.lgu.abc.core.module.selector.status.OpenedModuleSelector;
import com.lgu.abc.core.prototype.org.company.Company;

public class PluggableUtils {

	public static <P extends Pluggable> void add(P plugin, Set<P> plugins) {
		addPlugin(plugin, plugins);
	}
	
	public static <P extends Pluggable> void add(P plugin, List<P> plugins) {
		addPlugin(plugin, plugins);
		Collections.sort(plugins, SortableComparator.instance());
	}
	
	private static <P extends Pluggable> void addPlugin(P plugin, Collection<P> plugins) {
		for (P p : plugins)
			Validate.isTrue(!p.id().equals(plugin.id()), "plugin already exists. id: " + p.id());
		
		plugins.add(plugin);
	}
	
	public static <P extends ModulePluggable> P find(String pluginId, Company company, Collection<P> plugins) {
		for (P plugin : enabled(company, plugins)) {
			if (plugin.id().equals(pluginId)) return plugin;
		}
		
		return null;
	}
	
	public static <P extends ModulePluggable> List<P> find(ModuleSelectable selectable, Company company, Collection<P> plugins) {
		List<P> result = new ArrayList<P>();
		
		for (P plugin : plugins) {
			Module module = plugin.module(company);
			if (!selectable.canSelect(module)) continue;
			
			result.add(plugin);
		}
		
		return result;
	}
	
	public static <P extends ModulePluggable> List<P> opened(Company company, Collection<P> plugins) {
		return find(OpenedModuleSelector.instance(), company, plugins);
	}
	
	public static <P extends ModulePluggable> List<P> enabled(Company company, Collection<P> plugins) {
		return find(EnabledModuleSelector.instance(), company, plugins);
	}
	
	public static <P extends ModulePluggable> Set<P> all(Set<P> plugins) {
		return Collections.unmodifiableSet(plugins);
	}
	
}
