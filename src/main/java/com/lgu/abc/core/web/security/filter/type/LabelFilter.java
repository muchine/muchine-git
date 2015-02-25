package com.lgu.abc.core.web.security.filter.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.finder.AbstractFinder;
import com.lgu.abc.core.common.domain.label.Label;
import com.lgu.abc.core.common.domain.label.interfaces.LabelAttachable;
import com.lgu.abc.core.web.security.filter.base.AbstractFilter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;
import com.lgu.abc.core.web.security.filter.support.FilterValidate;

public class LabelFilter<T extends RootEntity, Q extends BaseEntity, L extends Label> extends AbstractFilter<T, Q>  {
	
	private final AbstractFinder<L> finder;
	
	public LabelFilter(AbstractFinder<L> finder) {
		this.finder = finder;
	}

	@Override
	public void filterEntity(T entity) throws InvalidParameterException {
		@SuppressWarnings("unchecked")
		LabelAttachable<L> attachable = (LabelAttachable<L>) entity;
		Collection<L> labels = attachable.labels();		
		
		if (CollectionUtils.isEmpty(labels)) return;
		
		List<L> filtered = new ArrayList<L>();
		for (L label : labels) {
			L found = finder.findById(label.getId());
			
			FilterValidate.isTrue(found != null, "label has not found. id: " + label.getId());
			filtered.add(found);
		}
		
		attachable.labels(filtered);
	}
	
}
