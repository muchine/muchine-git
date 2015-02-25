package com.lgu.abc.core.common.file.validation.spec;

import java.util.Collection;

import com.lgu.abc.core.common.infra.file.File;

/**
 * A specification describing that a file should exist which means, it should have already bean uploaded.
 * @author Sejoon Lim
 * @since 2014. 2. 13.
 *
 */
final class UploadedFileSpecification extends AbstractFileSpecification {

	@Override
	protected boolean validate(Collection<File> files) {
		for (File file : files) {
			if (file == null || file.isNull()) return false;
		}
		
		return true;
	}

}
