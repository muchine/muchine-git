package com.lgu.abc.core.plugin.proto.user.validation;

public abstract class AbstractLoginIdValidator implements LoginIdValidatable {

	protected AbstractLoginIdValidator(GlobalLoginIdValidator validator) {
		validator.add(this);
	}

}
