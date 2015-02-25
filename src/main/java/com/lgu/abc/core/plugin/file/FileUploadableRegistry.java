package com.lgu.abc.core.plugin.file;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

/**
 * A global container for all local file uploadable implementation.
 * @author Sejoon Lim
 * @since 2014. 3. 7.
 *
 */
@Component
public class FileUploadableRegistry {

	private final List<FileUploadable> uploadables = new ArrayList<FileUploadable>();
	
	/**
	 * Add local file uploadable implementation to the registry
	 * @param uploadable the uploadable implementation to add the registry
	 */
	public synchronized void add(FileUploadable uploadable) {
		Validate.isTrue(find(uploadable.name()) == null, "Uploadable already exists... name: " + uploadable.name());
		uploadables.add(uploadable);
	}
	
	/**
	 * Return the uplaodable matched with the given name.
	 * @param name the name to find an uploadable
	 * @return the uploadable implementation that has a given name
	 */
	public FileUploadable find(String name) {
		if (StringUtils.isEmpty(name)) return null;
		
		for (FileUploadable uploadable : uploadables) {
			if (name.equals(uploadable.name())) return uploadable;
		}
		
		return null;
	}
	
}
