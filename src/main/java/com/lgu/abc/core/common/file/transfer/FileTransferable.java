package com.lgu.abc.core.common.file.transfer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.vo.Size;

/**
 * A representation of file transference from temporary store to a permanent storage. Temporary store can be byte array in memory,
 * MultipartFile object coming from HTTP servlet multipart request, or File representation stored in another storage. The place
 * where a file will be located in the permanent storage is determined by FileUploadable. 
 * @author Sejoon Lim
 * @since 2014.02.25
 * @see org.springframework.web.multipart.MultipartFile
 * @see java.io.File
 * @see com.lgu.abc.core.plugin.file.FileUploadable
 */
public interface FileTransferable {

	/**
	 * Return the original file name in client's file system. This should not include path information
	 * @return the original file name
	 */
	String name();
	
	/**
	 * Return the size of the file
	 * @return the size of the file
	 * @see com.lgu.abc.core.common.vo.Size
	 */
	Size size();
	
	/**
	 * Transfer a temporary file to the given file destination in a permanent storage. This method should be called just once
	 * otherwise unexpected result like an exception may be returned since the destination file already exists.  
	 * @param destination the destination file
	 * @throws FileTransferException in case of failure to transfer a file to a permanent storage
	 */
	void transferTo(File destination) throws FileTransferException;
	
	/**
	 * Return an InputStream to read the contents of the file from.
	 * The user is responsible for closing the stream.
	 * @return the contents of the file as stream
	 * @throws IOException in case of access errors (if the temporary store fails)
	 */
	InputStream getInputStream() throws IOException;
	
}
