package com.lgu.abc.core.common.file.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.support.FileFinder;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.exception.CoreException;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.security.filter.exception.InvalidParameterException;

public class FileValidator {
	
	private static final String MESSAGE = "filter.error.file.invalid";
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final FileFinder finder;
	
	public FileValidator(FileFinder finder) {
		this.finder = finder;
	}

	public File validate(String id, Specification specification, User actor) throws InvalidParameterException {
		File file = new File(id);
		Collection<File> files = new ArrayList<File>();
		files.add(file);
		
		List<File> validated = validate(files, specification, actor);
		return validated.get(0);
	}
		
	public List<File> validate(Collection<File> files, Specification specification, User actor) throws InvalidParameterException  {
		List<File> retrieved = new ArrayList<File>();
		
		try {
			if (CollectionUtils.isEmpty(files)) return retrieved;
			for (File file : files) retrieved.add(finder.findById(file.getId()));
			
			if (!specification.isSatisfiedBy(retrieved)) 
				throw new InvalidParameterException(MESSAGE, actor.getLocale());
				
			return retrieved;
		}
		catch (CoreException e) {
			String message = e.getLocalizedMessage(actor.getLocale());
			logger.debug("file validation exception occured: " + e + ", message: " + message);
			throw new InvalidParameterException(message);
		}
	}
		
}
