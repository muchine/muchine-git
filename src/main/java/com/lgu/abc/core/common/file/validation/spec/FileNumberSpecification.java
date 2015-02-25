package com.lgu.abc.core.common.file.validation.spec;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.file.exception.FileNumberExcessException;
import com.lgu.abc.core.common.infra.file.File;

/**
 * A specification describing that files can be uploaded if and only if the number of files does not exceed the limit.
 * @author Sejoon Lim
 * @since 2014. 2. 13.
 *
 */
final class FileNumberSpecification extends AbstractFileSpecification {

	private final int maxFileNumber;
	
	FileNumberSpecification(int maxFileNumber) {
		Validate.isTrue(maxFileNumber >= 0);
		this.maxFileNumber = maxFileNumber;
	}
	
	@Override
	protected boolean validate(Collection<File> files) {
		if (maxFileNumber < files.size()) throw new FileNumberExcessException();
		return true;
	}

}
