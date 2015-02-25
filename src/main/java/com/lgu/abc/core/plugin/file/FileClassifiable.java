package com.lgu.abc.core.plugin.file;

import com.lgu.abc.core.prototype.org.user.User;

/**
 * A representation how to classify files. File classification is subordinate to file location and path. File location and path 
 * in FileUploadable interface is fixed value in all business domain module. However, file classification can be different in each module.
 * @author Sejoon Lim
 * @since 2014.02.25
 * @see com.lgu.abc.core.plugin.file.FileUploadable
 *
 */
public interface FileClassifiable {

	/**
	 * Return the sub-path to which an uploaded file should be stored based on file classification rule. The classification rule can
	 * use user's information like user, company and department information to create sub-path.
	 * @param user the actor uploading files
	 * @return the sub-path of a file
	 */
	String subpath(User user);
	
}
