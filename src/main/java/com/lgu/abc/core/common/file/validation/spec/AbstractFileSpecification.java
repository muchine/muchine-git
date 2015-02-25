package com.lgu.abc.core.common.file.validation.spec;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.spec.CompositeSpecification;

public abstract class AbstractFileSpecification extends CompositeSpecification {

	@Override
	public final boolean isSatisfiedBy(Object object) {
		@SuppressWarnings("unchecked")
		Collection<File> files = (Collection<File>) object;
		
		if (CollectionUtils.isEmpty(files)) return true;
		return validate(files);
	}

	protected abstract boolean validate(Collection<File> files);	
	
}
