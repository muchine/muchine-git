package com.lgu.abc.core.common.file.validation.spec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.file.exception.NoAvailableFileSpaceException;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.vo.Size;

/**
 * A specification describing that a file can be uplaoded if and only if there are available storage space.
 * @author Sejoon Lim
 * @since 2014. 2. 13.
 *
 */
final class AvailableSpaceSpecification extends AbstractFileSpecification {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final Size available;
	
	AvailableSpaceSpecification(Size available) {
		Validate.notNull(available, "available size is null.");
		this.available = available;
	}
	
	@Override
	protected boolean validate(Collection<File> files) {
		logger.debug("available: " + available);
		if (available.compareTo(sum(files)) < 0) throw new NoAvailableFileSpaceException();
		return true;
	}
	
	private Size sum(Collection<File> files) {
		List<Size> sizes = new ArrayList<Size>();		
		for (File file : files)	sizes.add(file.size());
		
		final Size sum = Size.sum(sizes.toArray(new Size[sizes.size()]));
		logger.debug("sum of files: " + sum);
		return sum;
	}

}
