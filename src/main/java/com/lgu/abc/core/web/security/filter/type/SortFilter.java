package com.lgu.abc.core.web.security.filter.type;

import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Sort.Order;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.web.security.filter.base.AbstractFilter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public class SortFilter<T extends RootEntity, Q extends BaseEntity> extends AbstractFilter<T, Q> {

	private final String[] properties;
	
	public SortFilter(String[] properties) {
		Validate.noNullElements(properties);
		this.properties = properties;
	}

	@Override
	public void filterQuery(Q query) throws InvalidParameterException {
		if (!(query instanceof BaseQuery)) return;
		
		BaseQuery casted = (BaseQuery) query;
		if (casted.getPageable() == null || casted.getPageable().getSort() == null) return;
		
		for (Order order : casted.getPageable().getSort()) {
			if (!contains(order.getProperty())) throw new InvalidParameterException("Invalid sort property: " + order.getProperty());
		}
	}
	
	private boolean contains(String property) {
		for (String e : properties) {
			if (e.equals(property)) return true;
		}
		
		return false;
	}

}
