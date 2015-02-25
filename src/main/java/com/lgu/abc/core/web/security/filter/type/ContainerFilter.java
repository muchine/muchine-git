package com.lgu.abc.core.web.security.filter.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.finder.AbstractFinder;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.ContainerAssignable;
import com.lgu.abc.core.web.security.filter.base.AbstractFilter;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;
import com.lgu.abc.core.web.security.filter.support.FilterValidate;

public class ContainerFilter<T extends RootEntity, Q extends BaseEntity, C extends Containable> extends AbstractFilter<T, Q> {
	
	public enum ContainerNumber {
		ANY, ONE, MANY;
	}

	private final AbstractFinder<C> finder;
	
	private final ContainerNumber number;
	
	public ContainerFilter(AbstractFinder<C> finder) {
		this(finder, ContainerNumber.ANY);
	}
	
	public ContainerFilter(AbstractFinder<C> finder, ContainerNumber number) {
		this.finder = finder;
		this.number = number;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void filterEntity(T entity) throws InvalidParameterException {
		final Collection<Containable> containers = entity.containers(); 
		
		validateContainerNumber(containers);
		if (CollectionUtils.isEmpty(containers)) return;
		
		List<C> containables = new ArrayList<C>();
		for (Containable c : containers) {
			C containable = finder.findById(c.getId());
			
			FilterValidate.isTrue(containable != null, "containable has not found. id: " + c.getId()); 
			containables.add(containable);
		}
		
		((ContainerAssignable<C>) entity).containers(containables);
	}
	
	private void validateContainerNumber(Collection<Containable> containers) throws InvalidParameterException {
		switch(number) {
			case ANY : return;
			case ONE : FilterValidate.one(containers, "container"); return;
			case MANY: FilterValidate.notNull(containers, "container"); return;
		}		
	}
	
}
