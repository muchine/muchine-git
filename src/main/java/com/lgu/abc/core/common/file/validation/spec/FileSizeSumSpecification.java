package com.lgu.abc.core.common.file.validation.spec;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.file.exception.FileSizeSumExcessException;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.vo.Size;

final class FileSizeSumSpecification extends AbstractFileSpecification {

	private final Size maxSumOfFileSize;
	
	FileSizeSumSpecification(Size maxSumOfFileSize) {
		Validate.notNull(maxSumOfFileSize, "max sum of file size is null.");
		this.maxSumOfFileSize = maxSumOfFileSize;
	}
	
	@Override
	protected boolean validate(Collection<File> files) {
		Size sum = new Size(0);
		for (File file : files) sum = sum.add(file.getVolume().getSize());
		
		if (sum.compareTo(maxSumOfFileSize) > 0) throw new FileSizeSumExcessException();
		return true;
	}

}
