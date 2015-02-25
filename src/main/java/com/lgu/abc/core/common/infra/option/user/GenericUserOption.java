package com.lgu.abc.core.common.infra.option.user;

import com.lgu.abc.core.common.infra.option.GenericOption;
import com.lgu.abc.core.prototype.org.user.User;

public class GenericUserOption extends GenericOption {

	public GenericUserOption() {}
	
	public GenericUserOption(User actor, String name, String value) {
		super(actor, name, value);
		setActor(actor);
	}
	
}
