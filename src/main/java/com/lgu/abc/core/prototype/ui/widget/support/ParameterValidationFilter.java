package com.lgu.abc.core.prototype.ui.widget.support;

import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.web.security.filter.base.AbstractFilter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;
import com.lgu.abc.core.web.security.filter.support.FilterValidate;

final class ParameterValidationFilter<T extends WidgetOption, Q extends WidgetOption> extends AbstractFilter<T, Q> {

	@Override
	public void filterEntity(T entity) throws InvalidParameterException {
		FilterValidate.notNull(entity.getType(), "widget type");
	}
	
}
