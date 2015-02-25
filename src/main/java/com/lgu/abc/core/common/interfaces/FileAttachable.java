package com.lgu.abc.core.common.interfaces;

import java.util.Collection;

import com.lgu.abc.core.common.infra.file.File;

/**
 * A representation of object where files can be attached
 * @author Sejoon Lim
 * @since 2014. 2. 13.
 *
 */
public interface FileAttachable {

	/**
	 * Return the currently attached files
	 * @return the currently attached files
	 */
	Collection<File> files();
	
	/**
	 * Attach the given files
	 * @param files the files to attach
	 */
	void attach(Collection<File> files);
	
}
