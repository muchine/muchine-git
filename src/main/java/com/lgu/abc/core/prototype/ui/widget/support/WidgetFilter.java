package com.lgu.abc.core.prototype.ui.widget.support;

import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.web.security.filter.base.ChainableFilter;
import com.lgu.abc.core.web.security.filter.type.DefaultFilter;

public class WidgetFilter<T extends WidgetOption, Q extends WidgetOption> extends ChainableFilter<T, Q> {

	public WidgetFilter() {
		super.add(new DefaultFilter<T, Q>())
			 .add(new ParameterValidationFilter<T, Q>());
	}

}
