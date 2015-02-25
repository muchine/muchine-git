package com.lgu.abc.core.module.view;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class ModuleViewSet {

	private final List<ModuleView> views = new ArrayList<ModuleView>();
	
	public ModuleViewSet(Iterable<Module> modules, User actor) {
		for (Module module : modules) views.add(new ModuleView(module, actor));
	}
	
}
