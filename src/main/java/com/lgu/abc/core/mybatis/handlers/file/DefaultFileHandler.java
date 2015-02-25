package com.lgu.abc.core.mybatis.handlers.file;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.utils.EntityUtils;
import com.lgu.abc.core.common.file.FileManager;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.annotation.file.Files;
import com.lgu.abc.core.support.annotation.file.Images;

@Component
public class DefaultFileHandler implements FileHandler {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private FileManager manager;
	
	@Override
	public void handleFile(Object entity, User actor) {
		Validate.notNull(entity, "entity is null.");
		logger.trace("Handling files... entity class: " + entity.getClass());
		
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) process(field, entity, actor);
	}
	
	@SuppressWarnings("unchecked")
	private void process(Field field, Object entity, User actor) {
		try {
			field.setAccessible(true);
			
			final Object value = field.get(entity);
			if (value == null) return;
			
			if (!isSupportedType(field.getType())) {
				if (EntityUtils.isDomainObject(value)) handleFile(value, actor);
				return;
			}
			
			if (!isFileAnnotated(field)) return;
			
			if (List.class.isAssignableFrom(value.getClass()))
				field.set(entity, confirmAll((List<Object>) value, actor));
			else
				field.set(entity, confirm(value, actor));
		}
		catch  (IllegalAccessException e) {
			throw new InvalidDataAccessResourceUsageException("Can't access field while processing files. entity: " + entity);
		}
	}
	
	private boolean isSupportedType(Class<?> type) {
		return File.class.isAssignableFrom(type) || List.class.isAssignableFrom(type);
	}
	
	private boolean isFileAnnotated(Field field) {
		return field.isAnnotationPresent(Images.class) || field.isAnnotationPresent(Files.class);
	}
		
	private List<File> confirmAll(List<Object> field, User actor) {
		List<File> files = new ArrayList<File>();
		
		for (Object e : field) {
			File confirmed = confirm(e, actor);
			if (confirmed != null) files.add(confirmed);
		}
		
		return files;
	}
	
	private File confirm(Object field, User actor) {
		if (field == null) return null;
		Validate.isTrue(field instanceof File, "Field object should be File type. field: " + field);
		
		File file = (File) field;
		if (file.isNull()) return null;
		
		file.setActor(actor);
		logger.info("Handle file... id: " + file.getId());
		manager.confirm(file);
		
		return file;
	}

}
