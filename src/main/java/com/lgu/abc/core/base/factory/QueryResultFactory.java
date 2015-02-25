package com.lgu.abc.core.base.factory;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.u2ware.springfield.domain.EntityPageImpl;

public class QueryResultFactory {

	public static <T> Iterable<T> create(List<T> entities, Iterable<T> page, Pageable pageable) {
		if (page instanceof EntityPageImpl) {
			EntityPageImpl<T> casted = (EntityPageImpl<T>) page;
			return new EntityPageImpl<T>(entities, pageable, (int) casted.getTotalElements());
		}

		return entities;
	}
	
}
