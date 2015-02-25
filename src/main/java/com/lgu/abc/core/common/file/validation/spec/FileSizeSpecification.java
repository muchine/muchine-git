package com.lgu.abc.core.common.file.validation.spec;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.file.exception.FileSizeExcessException;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.vo.Size;

/**
 * A specification describing that a file can be uploaded if and only if the file size does not exceed the limit.
 * @author Sejoon Lim
 * @since 2014. 2. 13.
 *
 */
final class FileSizeSpecification extends AbstractFileSpecification {

	private final Size maxFileSize;
	
	FileSizeSpecification(Size maxFileSize) {
		Validate.notNull(maxFileSize, "max file size is null.");
		this.maxFileSize = maxFileSize;
	}
	
	@Override
	protected boolean validate(Collection<File> files) {
		for (File file : files) {
			if (maxFileSize.compareTo(file.size()) < 0) throw new FileSizeExcessException();
		}
		
		return true;
	}

}
