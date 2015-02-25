package com.lgu.abc.core.common.file;

import java.util.Date;

import com.lgu.abc.core.common.file.download.FileResource;
import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.file.exception.IllegalFileStatusException;
import com.lgu.abc.core.common.file.exception.UnknownFileTypeException;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * This interface represents how to manage the life cycle of files in a permanent storage and a database. 
 * 
 * <p>The life cycle begins with uploading or copying file to a storage. However, if the file is not confirmed, 
 * it is regarded as a temporary file so removed after a certain period. Once confirmed, the file starts living 
 * in a storage. the file can be replaced in the middle of life cycle. the life of file ends with invalidation.  
 * @author Sejoon Lim
 * @since 2014. 2. 5.
 *
 */
public interface FileManager {

	/**
	 * Upload a file by using given FileUpload object.
	 * @param type the name of FileUploadable implementation which determines file location in a storage
	 * @param upload the representation of file upload
	 * @return the file instance created as a result of upload operation
	 * @throws FileTransferException in case of failure to transfer a file to a permanent storage
	 * @throws UnknownFileTypeException in case of an invalid FileUploadable name
	 * @see com.lgu.abc.core.plugin.file.FileUploadable
	 */
	File upload(String type, FileUpload upload) throws FileTransferException, UnknownFileTypeException;
	
	/**
	 * Create a copy of a given file.
	 * @param type the name of FileUploadable implementation which determines file location in a storage
	 * @param file the source file to copy
	 * @return the file instance created as a result of copy operation
	 * @throws FileTransferException in case of failure to transfer a file to a permanent storage
	 * @throws UnknownFileTypeException in case of an invalid FileUploadable name
	 * @see com.lgu.abc.core.plugin.file.FileUploadable
	 */
	File copy(String type, File file) throws FileTransferException, UnknownFileTypeException;
	
	/**
	 * Confirm the given file to keep permanently. If the file has already been confirmed, this method does nothing.
	 * @param file the file to confirm
	 */
	void confirm(File file);
	
	/**
	 * Update the property of temporary file. The file property can be changed until confirmation. However any attempts
	 * to change file property is not allowed once after the file is confirmed.
	 * @param file the file to update
	 * @throws IllegalFileStatusException in case when the file had already confirmed.
	 */
	void update(File file) throws IllegalFileStatusException;
	
	/**
	 * Download a file by using file id
	 * @param fileId the id of uploaded file
	 * @param actor the user who requests file download
	 * @return the representation of downloaded file, which contains file metadata and content
	 */
	FileResource download(String fileId, User actor);
	
	/**
	 * Invalidate the given file to remove from a permanent storage. Event though this method is called, 
	 * the file might not be removed immediately depending on implementation.
	 * @param file the file to invalidate
	 * @param actor the requester for file invalidation
	 */
	void invalidate(File file, User actor);
	
	/**
	 * Create the temporal file link that can be invoked from external world.
	 * @param fileId a file id to create link
	 * @return a created link
	 */
	String createExternalLink(String fileId, Date expired);
	
}
