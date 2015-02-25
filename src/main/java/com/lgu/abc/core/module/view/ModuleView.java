package com.lgu.abc.core.module.view;

import lombok.Data;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.ModuleUtils;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class ModuleView {

	private final Module entity;
	
	private final User actor;
	
	public ModuleView(Module entity, User actor) {
		this.entity = entity;
		this.actor = actor;
	}
	
	public String getName() {
		return entity.name().getValue(actor.getLocale());
	}
	
	public String getUrl() {
		 return isEnabled() ? entity.home() : "#";
	}
	
	public boolean isEnabled() {
		return ModuleUtils.isEnabled(entity);
	}
	
	public boolean isHidden() {
		return !isEnabled() && entity.render().isHiddenIfDisabled();
	}

}
