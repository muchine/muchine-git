package com.lgu.abc.core.plugin.file;

import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.vo.Size;
/**
 * A representation of metadata to upload file. A module that needs a file attachment functionality should implement this interface. 
 * @author Sejoon Lim
 * @since 2014.02.25
 *
 */
public interface FileUploadable {

	/**
	 *  Return the name of a file uploadable implementation.
	 *  @return the name of a file uploadable implementation
	 */
	String name();
	
	/**
	 * Return the root location on which an uploaded file should be stored. The location is determined based on uploaded 
	 * file representation.
	 * @param upload a representation of uploaded file
	 * @return the root location of file
	 */
	String location(FileUpload upload);
	
	/**
	 * Return the path to which an uploaded file should be stored. Path is subordinate to location, which means a full file path 
	 * looks like [location][<b>path</b>][classifiable][filename]. In general a path represents business domain name of file uploadable 
	 * implementation.
	 * @return the path of file
	 */
	String path();
	
	/**
	 * Return the classification of sub-path to which an uploaded file should be stored. File classification is subordinate to location
	 * and path, which means a full file path looks like [location][path][<b>classifiable</b>][filename]. There may be different file
	 * classifications based on day, company identity, user identity, etc. 
	 * @return the classification implementation of the file
	 */
	FileClassifiable classifiable();
	
	/**
	 * Return the threshold for a large volume file. A file with the size over this threshold will be regarded as a large volume file.
	 * @return the threshold for a large volume file
	 */
	Size threshold();
	
	/**
	 * Complete the upload operation if any additional process should be done.
	 * @param uploaded the uploaded file
	 * @return the completed file to which any additional operation has been done after upload.
	 */
	File complete(File uploaded);

}
